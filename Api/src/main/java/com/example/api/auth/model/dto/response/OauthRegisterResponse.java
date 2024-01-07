package com.example.api.auth.model.dto.response;

import com.example.api.auth.model.vo.TokenVo;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OauthRegisterResponse {
    @JsonUnwrapped
    private TokenVo tokenVo;

    @Builder
    private OauthRegisterResponse(TokenVo tokenVo) {
        this.tokenVo = tokenVo;
    }

    public static OauthRegisterResponse of(
            String accessToken, Long accessTokenAge, String refreshToken, Long refreshTokenAge) {
        return OauthRegisterResponse.builder()
                .tokenVo(TokenVo.of(accessToken, accessTokenAge, refreshToken, refreshTokenAge))
                .build();
    }

    public static OauthRegisterResponse from(OauthSignInResponse oauthSignInResponse) {
        return OauthRegisterResponse.builder().tokenVo(oauthSignInResponse.getTokenVo()).build();
    }
}