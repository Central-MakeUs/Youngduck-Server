package com.example.api.auth.model.dto;

import com.example.domains.user.enums.OauthProvider;
import com.example.oauth.dto.KakaoInfoResponseDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OauthUserInfoDto {
    private String email;
    private String name;
    private OauthProvider oauthProvider;

    @Builder
    private OauthUserInfoDto(String email, String name, OauthProvider oauthProvider) {
        this.email = email;
        this.name = name;
        this.oauthProvider = oauthProvider;
    }

    public static OauthUserInfoDto fromKakao(KakaoInfoResponseDto response) {
        return OauthUserInfoDto.builder()
                .email(response.getEmail())
                .name(response.getNickName())
                .oauthProvider(OauthProvider.KAKAO)
                .build();
    }
}

