package com.example.api.auth.model.dto.response;

import com.example.oauth.dto.KakaoTokenResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OauthTokenResponse {
    private String accessToken;
    private String refreshToken;
    private String idToken;

    @Builder
    private OauthTokenResponse(String accessToken, String refreshToken, String idToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.idToken = idToken;
    }

    public static OauthTokenResponse from(KakaoTokenResponse kakaoTokenResponse) {
        return OauthTokenResponse.builder()
                .idToken(kakaoTokenResponse.getIdToken())
                .refreshToken(kakaoTokenResponse.getRefreshToken())
                .accessToken(kakaoTokenResponse.getAccessToken())
                .build();
    }

}