package com.example.api.auth.model.dto.response;

import com.example.api.auth.model.vo.TokenVo;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OauthSignInResponse {
    @Schema(description = "로그인 가능 여부")
    private boolean canLogin;

    @Schema(description = "idToken, 로그인 되었을 경우 null")
    private String idToken;

    @JsonUnwrapped
    private TokenVo tokenVo;

    @Builder
    private OauthSignInResponse(boolean canLogin, String idToken, TokenVo tokenVo) {
        this.canLogin = canLogin;
        this.idToken = idToken;
        this.tokenVo = tokenVo;
    }

    public static OauthSignInResponse of(
            boolean canLogin,
            String idToken,
            String accessToken,
            Long accessTokenAge,
            String refreshToken,
            Long refreshTokenAge) {
        return OauthSignInResponse.builder()
                .canLogin(canLogin)
                .idToken(idToken)
                .tokenVo(TokenVo.of(accessToken, accessTokenAge, refreshToken, refreshTokenAge))
                .build();
    }

    public static OauthSignInResponse cannotLogin(String idToken) {
        return OauthSignInResponse.builder()
                .canLogin(false)
                .idToken(idToken)
                .tokenVo(TokenVo.of(null, null, null, null))
                .build();
    }
}