package com.example.oauth.client;

import com.example.oauth.config.KakaoInfoConfig;
import com.example.oauth.dto.KakaoInfoResponseDto;
import com.example.oauth.dto.KakaoUnlinkTarget;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "KakaoInfoClient",
        url = "https://kapi.kakao.com",
        configuration = KakaoInfoConfig.class)
@Component
public interface KakaoInfoClient {
    @PostMapping(path = "/v1/user/unlink", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    void unlinkUser(
            @RequestHeader("Authorization") String adminKey, KakaoUnlinkTarget unlinkKaKaoTarget);

    @GetMapping("/v2/user/me")
    KakaoInfoResponseDto kakaoUserInfo(@RequestHeader("Authorization") String accessToken);
}