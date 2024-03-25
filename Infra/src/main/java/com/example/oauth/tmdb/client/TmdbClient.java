package com.example.oauth.tmdb.client;

import com.example.oauth.apple.config.AppleOAuthConfig;
import com.example.oauth.tmdb.config.TmdbClientConfig;
import com.example.oauth.tmdb.dto.TdmbResponseDto;
import feign.Headers;
import feign.Param;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(
        name = "TmdbClient",
        url = "http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2",
        configuration = TmdbClientConfig.class)
public interface TmdbClient {
    @GetMapping
    String getMovieData(@RequestParam("movieId") String movieId, @RequestParam("movieSeq") String movieSeq, @RequestParam("detail") String detail, @RequestParam("ServiceKey") String ServiceKey);
}
