package com.example.api.recommendedPopcorn.service;

import com.example.adaptor.UseCase;
import com.example.api.config.security.SecurityUtil;
import com.example.api.recommendedPopcorn.dto.request.RecommendedPopcornRequest;
import com.example.api.recommendedPopcorn.dto.response.RecommendedPopcornResponse;
import com.example.domains.recommendedPopcorn.adaptor.RecommendedPopcornAdaptor;
import com.example.domains.recommendedPopcorn.entity.RecommendedPopcorn;
import com.example.domains.recommendedPopcornUser.adaptor.RecommendedPopcornUserAdaptor;
import com.example.domains.recommendedPopcornUser.entity.RecommendedPopcornUser;
import com.example.domains.user.adaptor.UserAdaptor;
import com.example.domains.user.entity.User;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.core.env.Environment;

import java.io.IOException;

@UseCase
@RequiredArgsConstructor
public class PostRecommendPopcornUseCase {
    private final RecommendedPopcornAdaptor recommendedPopcornAdaptor;
    private final UserAdaptor userAdaptor;
    private final RecommendedPopcornUserAdaptor recommendedPopcornUserAdaptor;

    @Value("${TMDB_SECRET}")
    String tmdb ;
    public void execute(RecommendedPopcornRequest request) throws IOException {

        validateMovieId(request.getMovieId());

        System.out.println(tmdb);
        JsonParser parser = new JsonParser();
        JsonElement jsonObject = parser.parse(request.getMovieId());

        System.out.println(jsonObject);
        getApi(jsonObject,request);
        //RecommendedPopcorn.of(request.getMovieId(),request.getReason())
    }

    private void validateMovieId(String movieId) {
        recommendedPopcornAdaptor.checkExists(movieId);
    }

    private void getApi(
            JsonElement movieId,RecommendedPopcornRequest popcornRequest) throws IOException {

        OkHttpClient client = new OkHttpClient();


        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/movie/" + movieId + "?language=ko&api_key=" + tmdb)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer " + tmdb)
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println(response.message());
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            String responseBody = response.body().string();

            // JSON 파싱
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseBody);

             //필요한 정보 추출
            String originalTitle = rootNode.path("original_title").asText();
            String overview = rootNode.path("overview").asText();
            String posterPath = rootNode.path("poster_path").asText();

            // 추출한 정보 활용
            System.out.println("Original Title: " + originalTitle);
            System.out.println("Overview: " + overview);
            System.out.println("Poster Path: " + posterPath);

            postRecommendation(movieId.toString(),originalTitle,overview,posterPath,popcornRequest);
        }
    }

    public void postRecommendation(String movieId,String originalTitle, String overview, String posterPath, RecommendedPopcornRequest popcornRequest) {
        Long userId = SecurityUtil.getCurrentUserId();
        User user = userAdaptor.findById(userId);

        final RecommendedPopcorn recommendedPopcorn = RecommendedPopcorn.of(movieId
        ,originalTitle,posterPath,overview,popcornRequest.getReason());
        RecommendedPopcorn recommendedPopcornResult = recommendedPopcornAdaptor.save(recommendedPopcorn);
        proceedSaving(recommendedPopcornResult,user);
    }
    private void proceedSaving(RecommendedPopcorn recommendedPopcorn, User user) {
        final RecommendedPopcornUser  recommendedPopcornUser = RecommendedPopcornUser.of(true,user,recommendedPopcorn);
        recommendedPopcornUserAdaptor.save(recommendedPopcornUser);
    }
}
