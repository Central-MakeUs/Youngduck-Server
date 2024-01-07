package com.example.api.auth.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenVo {
    @Schema(description = "어세스 토큰")
    private final String accessToken;

    private final Long accessTokenAge;

    @Schema(description = "리프레쉬 토큰")
    private final String refreshToken;

    private final Long refreshTokenAge;

    @Builder
    private TokenVo(
            String accessToken, Long accessTokenAge, String refreshToken, Long refreshTokenAge) {
        this.accessToken = accessToken;
        this.accessTokenAge = accessTokenAge;
        this.refreshToken = refreshToken;
        this.refreshTokenAge = refreshTokenAge;
    }

    public static TokenVo of(
            String accessToken, Long accessTokenAge, String refreshToken, Long refreshTokenAge) {
        return TokenVo.builder()
                .accessToken(accessToken)
                .accessTokenAge(accessTokenAge)
                .refreshToken(refreshToken)
                .refreshTokenAge(refreshTokenAge)
                .build();
    }
}