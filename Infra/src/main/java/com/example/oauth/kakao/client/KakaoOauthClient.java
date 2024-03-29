package com.example.oauth.kakao.client;

import com.example.oauth.kakao.config.KakaoKauthConfig;
import com.example.oauth.kakao.dto.KakaoTokenResponse;
import com.example.oauth.kakao.dto.OIDCPublicKeysResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(
        name = "KakaoAuthClient",
        url = "https://kauth.kakao.com",
        configuration = KakaoKauthConfig.class)
@Component
public interface KakaoOauthClient {

    @PostMapping(
            "/oauth/token?grant_type=authorization_code&client_id={CLIENT_ID}&redirect_uri={REDIRECT_URI}&code={CODE}&client_secret={CLIENT_SECRET}")
    KakaoTokenResponse kakaoAuth(
            @PathVariable("CLIENT_ID") String clientId,
            @PathVariable("REDIRECT_URI") String redirectUri,
            @PathVariable("CODE") String code,
            @PathVariable("CLIENT_SECRET") String client_secret);


    @GetMapping("/.well-known/jwks.json")
    OIDCPublicKeysResponse getKakaoOIDCOpenKeys();
}