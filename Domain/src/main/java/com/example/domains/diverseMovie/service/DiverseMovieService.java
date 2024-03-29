package com.example.domains.diverseMovie.service;

import com.example.adaptor.DomainService;
import com.example.domains.diverseMovie.adaptor.DiverseMovieAdaptor;
import com.example.domains.diverseMovie.entity.DiverseMovie;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@DomainService
@RequiredArgsConstructor
public class DiverseMovieService {
    private final DiverseMovieAdaptor diverseMovieAdaptor;

    @Value("${API_KEY}")
    String apiKey;

    public void getTopRated() {
        OkHttpClient client = new OkHttpClient();
        // 현재 날짜를 얻어오기
        String currentDate = getFirstDayOfWeek();

        // URL 구성
        String url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key="+ apiKey +"&multiMovieYn=Y&targetDt=" + currentDate + "&itemPerPage=5";

        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                String responseData = response.body().string();
                saveMovie(responseData);
            } else {
                System.out.println("Request failed with code: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void saveMovie(String responseData) throws JsonProcessingException {
        // JSON 파싱
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode rootNode = objectMapper.readTree(responseData);
            JsonNode dailyBoxOfficeList = rootNode.path("boxOfficeResult").path("dailyBoxOfficeList");

            if (dailyBoxOfficeList.isArray()) {
                for (JsonNode movieData : dailyBoxOfficeList) {
                    String rank = movieData.path("rank").asText();
                    String movieNm = movieData.path("movieNm").asText();

                    final DiverseMovie diverseMovie = DiverseMovie.of(movieNm,rank);
                    diverseMovieAdaptor.save(diverseMovie);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            // 예외 처리 로직 추가
        }

    }

//    public void getImages(String responseData) {
//        // JSON 데이터를 BoxOfficeResult 객체로 매핑
//        BoxOfficeResult boxOfficeResult = parseJson(responseData);
//
//        // "movieNm" 속성을 가져와서 리스트로 만들기
//        List<String> movieNames = extractMovieNames(boxOfficeResult);
//
//        // 결과 출력
//        System.out.println("Movie Names: " + movieNames);
//        getImageUrl(movieNames);
//    }

    private static String getCurrentDate() {
        // 현재 날짜를 문자열로 변환
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return dateFormat.format(new Date());
    }

    private static String getFirstDayOfWeek() {
        // 현재 날짜 설정
        Calendar calendar = Calendar.getInstance();
        // 현재 날짜를 그 주의 첫 날(일요일)로 설정
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

        // 현재 날짜를 문자열로 변환
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return dateFormat.format(calendar.getTime());
    }
}
