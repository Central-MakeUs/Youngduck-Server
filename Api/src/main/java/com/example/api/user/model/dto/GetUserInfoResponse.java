package com.example.api.user.model.dto;

import com.example.domains.user.entity.User;
import com.example.domains.user.enums.OauthProvider;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GetUserInfoResponse {
    @Schema(defaultValue = "asd@asd.com", description = "이메일")
    private String email;

    @Schema(defaultValue = "이름", description = "유저 이름")
    private String name;

    @Schema(defaultValue = "닉네임", description = "닉네임")
    private String nickname;

    @Schema(defaultValue = "KAKAO", description = "oauth")
    private OauthProvider oauthProvider;

    @Builder
    public GetUserInfoResponse(
            String email,
            String name,
            String nickname,
            OauthProvider oauthProvider) {
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.oauthProvider = oauthProvider;
    }

    public static GetUserInfoResponse from(User user) {
        return GetUserInfoResponse.builder()
                .email(user.getOauthInfo().getEmail())
                .nickname(user.getNickname())
                .oauthProvider(user.getOauthInfo().getProvider())
                .build();
    }
}
