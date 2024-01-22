package com.example.api.Popcorn.service;

import com.example.adaptor.UseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@UseCase
@RequiredArgsConstructor
public class GetTopRatedMovies {

    public void getTopRated() {
        OkHttpClient client = new OkHttpClient();
        // 현재 날짜를 얻어오기
        String currentDate = getCurrentDate();

        // URL 구성
        String url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=1eac3f860b5d6af757c4b409321d9f74&multiMovieYn=Y&targetDt=" + currentDate + "&itemPerPage=5";

        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                String responseData = response.body().string();
                System.out.println("Response Data:");
                System.out.println(responseData);
                //getImages(responseData);
            } else {
                System.out.println("Request failed with code: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
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
//    private static BoxOfficeResult parseJson(String jsonString) {
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            return objectMapper.readValue(jsonString, BoxOfficeResult.class);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    private static List<String> extractMovieNames(BoxOfficeResult boxOfficeResult) {
//        List<String> movieNames = new ArrayList<>();
//        if (boxOfficeResult != null && boxOfficeResult.getBoxOfficeResult() != null
//                && boxOfficeResult.getBoxOfficeResult().getDailyBoxOfficeList() != null) {
//            for (DailyBoxOffice dailyBoxOffice : boxOfficeResult.getBoxOfficeResult().getDailyBoxOfficeList()) {
//                if (dailyBoxOffice != null && dailyBoxOffice.getMovieNm() != null) {
//                    movieNames.add(dailyBoxOffice.getMovieNm());
//                }
//            }
//        }
//        return movieNames;
//    }
}
