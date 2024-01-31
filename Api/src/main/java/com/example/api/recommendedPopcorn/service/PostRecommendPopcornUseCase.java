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
import io.swagger.v3.core.util.Json;
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

    @Value("${KDMB}")
    String tmdb ;
    public void execute(RecommendedPopcornRequest request) throws IOException {

        validateMovieId(request.getMovieId());

        System.out.println(tmdb);
        JsonParser parser = new JsonParser();
        JsonElement jsonObject = parser.parse(request.getMovieId());
        String movieTypeWithoutQuotes = request.getMovieType().replaceAll("\"", "");

        getApi(jsonObject,request,movieTypeWithoutQuotes);
        //RecommendedPopcorn.of(request.getMovieId(),request.getReason())
    }

    private void validateMovieId(String movieId) {
        recommendedPopcornAdaptor.checkExists(movieId);
    }

    private void getApi(
            JsonElement movieId, RecommendedPopcornRequest popcornRequest, String movieType) throws IOException {

        OkHttpClient client = new OkHttpClient();


        Request request = new Request.Builder()
                .url("http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2&movieId=" + movieType + "&movieSeq=" + movieId + "&detail=Y&ServiceKey=" + tmdb)
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

            // Extracting the first movie's information
            JsonNode movieData = rootNode.path("Data").path(0).path("Result").path(0);

            // Extracting specific information
            String title = movieData.path("title").asText();
            String directorNm = movieData.path("directors").path("director").path(0).path("directorNm").asText();
            String plotText = movieData.path("plots").path("plot").path(0).path("plotText").asText();
            String firstPosterUrl = movieData.path("posters").asText().split("\\|")[0];

            // Displaying the extracted information
            System.out.println("Title: " + title);
            System.out.println("Director: " + directorNm);
            System.out.println("Plot: " + plotText);
            System.out.println("First Poster URL: " + firstPosterUrl);

            postRecommendation(movieId.toString(),title,plotText,firstPosterUrl,directorNm,popcornRequest);
        }
    }

    public void postRecommendation(String movieId,String originalTitle, String overview, String posterPath,String directorNm, RecommendedPopcornRequest popcornRequest) {
        Long userId = SecurityUtil.getCurrentUserId();
        User user = userAdaptor.findById(userId);

        final RecommendedPopcorn recommendedPopcorn = RecommendedPopcorn.of(movieId
        ,originalTitle,posterPath,overview,directorNm,popcornRequest.getReason());
        RecommendedPopcorn recommendedPopcornResult = recommendedPopcornAdaptor.save(recommendedPopcorn);
        proceedSaving(recommendedPopcornResult,user);
    }
    private void proceedSaving(RecommendedPopcorn recommendedPopcorn, User user) {
        final RecommendedPopcornUser  recommendedPopcornUser = RecommendedPopcornUser.of(true,user,recommendedPopcorn);
        recommendedPopcornUserAdaptor.save(recommendedPopcornUser);
    }
}
