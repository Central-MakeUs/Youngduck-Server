package com.example.api.user.model.dto;

import com.example.adaptor.ValidEnum;
import com.example.domains.common.StringListConverter;
import com.example.domains.user.entity.User;
import com.example.domains.user.enums.Genre;
import com.example.domains.user.enums.OauthProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class GetUserInfoResponse {
    @Schema(defaultValue = "asd@asd.com", description = "이메일")
    private String email;

    @Schema(defaultValue = "이름", description = "유저 이름")
    private String name;

    @Schema(defaultValue = "닉네임", description = "닉네임")
    private String nickname;
    @Schema(defaultValue = "5", description = "프로필 랜덤 사진 번호")
    private int profileImgNum;

    @Schema(defaultValue = "KAKAO", description = "oauth")
    private OauthProvider oauthProvider;


//    @Schema(description = "좋아하는 영화 장르", implementation = Genre.class, allowableValues = "MELLO, COMEDY, ROCO, ACTION, WEST, GANG, NOIRE, SUSPENSE, THRILLER, HORROR, WAR, SF, DETECTIVE, FANTASY, ADVENTURE")
//    private List<Genre> genres;

    @Builder
    public GetUserInfoResponse(
            String email,
            String name,
            String nickname,
            int profileImgNum,
            OauthProvider oauthProvider) {
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.profileImgNum = profileImgNum;
        this.oauthProvider = oauthProvider;
    }

    public static GetUserInfoResponse from(User user) {
        return GetUserInfoResponse.builder()
                .email(user.getOauthInfo().getEmail())
                .nickname(user.getNickname())
                .profileImgNum(user.getProfileImgNum())
                .oauthProvider(user.getOauthInfo().getProvider())
                .build();
    }
}
