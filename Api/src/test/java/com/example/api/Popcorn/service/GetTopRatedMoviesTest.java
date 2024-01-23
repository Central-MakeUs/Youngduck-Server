package com.example.api.Popcorn.service;
import com.example.api.diverseMovie.service.GetTopRatedMovies;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;


public class GetTopRatedMoviesTest {

//    @Test
//    public void testGetTopRated() throws IOException {
//        // Arrange
//        OkHttpClient mockHttpClient = mock(OkHttpClient.class);
//        Call mockCall = mock(Call.class);
//        Response mockResponse = mock(Response.class);
//
//        when(mockHttpClient.newCall(ArgumentMatchers.any(Request.class))).thenReturn(mockCall);
//        when(mockCall.execute()).thenReturn(mockResponse);
//        when(mockResponse.isSuccessful()).thenReturn(true);
//        System.out.println(mockResponse.body());
//
//        //GetTopRatedMovies getTopRatedMovies = new GetTopRatedMovies();
//
//        // Act
//        getTopRatedMovies.getTopRated();
//
//        // Assert
//        // 여기에서는 특별한 응답에 대한 구체적인 검증을 추가할 수 있습니다.
//        // 예를 들어, 반환된 데이터의 형식이나 내용을 확인할 수 있습니다.
//    }
}