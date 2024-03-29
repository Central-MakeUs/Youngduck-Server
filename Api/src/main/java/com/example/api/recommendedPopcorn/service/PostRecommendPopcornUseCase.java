package com.example.api.recommendedPopcorn.service;

import com.example.adaptor.UseCase;
import com.example.api.config.security.SecurityUtil;
import com.example.api.recommendedPopcorn.dto.request.RecommendedPopcornRequest;
import com.example.domains.recommendedPopcorn.adaptor.RecommendedPopcornAdaptor;
import com.example.domains.recommendedPopcorn.entity.RecommendedPopcorn;
import com.example.domains.recommendedPopcornUser.adaptor.RecommendedPopcornUserAdaptor;
import com.example.domains.recommendedPopcornUser.entity.RecommendedPopcornUser;
import com.example.domains.user.adaptor.UserAdaptor;
import com.example.domains.user.entity.User;
import com.example.oauth.tmdb.client.TmdbClient;
import com.example.oauth.tmdb.dto.TdmbResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

@UseCase
@RequiredArgsConstructor
public class PostRecommendPopcornUseCase {
    private final RecommendedPopcornAdaptor recommendedPopcornAdaptor;
    private final UserAdaptor userAdaptor;
    private final TmdbClient tmdbClient;
    private final RecommendedPopcornUserAdaptor recommendedPopcornUserAdaptor;

    @Value("${KDMB}")
    String tmdb ;
    public void execute(RecommendedPopcornRequest request) throws IOException {

        validateMovieId(request.getMovieId());

        JsonParser parser = new JsonParser();
        JsonElement jsonObject = parser.parse(request.getMovieId());
        String movieTypeWithoutQuotes = request.getMovieType().replaceAll("\"", "");

        //TODO
        String responseString = tmdbClient.getMovieData(movieTypeWithoutQuotes,request.getMovieId(),"Y",tmdb);
        changeToObject(responseString,request);
        //RecommendedPopcorn.of(request.getMovieId(),request.getReason())
    }

    private void changeToObject(String responseString,RecommendedPopcornRequest request) throws JsonProcessingException {
        //값 처리하는 로직
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(responseString);
        //4. To JsonObject

        JsonNode movieData = rootNode.path("Data").path(0).path("Result").path(0);


        String title = movieData.path("title").asText();
        title = title.trim();
        String directorNm = movieData.path("directors").path("director").path(0).path("directorNm").asText();
        String plotText = movieData.path("plots").path("plot").path(0).path("plotText").asText();
        String firstPosterUrl = movieData.path("posters").asText().split("\\|")[0];
        postRecommendation(request.getMovieId(),title,plotText,firstPosterUrl,directorNm,request);
    }

    private void validateMovieId(String movieId) {
        recommendedPopcornAdaptor.checkExists(movieId);
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
            title = title.trim();
            String directorNm = movieData.path("directors").path("director").path(0).path("directorNm").asText();
            String plotText = movieData.path("plots").path("plot").path(0).path("plotText").asText();
            String firstPosterUrl = movieData.path("posters").asText().split("\\|")[0];

            postRecommendation(movieId.toString(),title,plotText,firstPosterUrl,directorNm,popcornRequest);
        }
    }

}
