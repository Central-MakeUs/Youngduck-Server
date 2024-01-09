package com.example.domains.user.entity;

import com.example.domains.common.StringListConverter;
import com.example.domains.common.model.BaseTimeEntity;
import com.example.domains.user.enums.*;
import com.example.error.exception.ServerForbiddenException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @NotNull
    private String nickname;
    @Embedded
    private OauthInfo oauthInfo;


    @Enumerated(EnumType.STRING)
    private UserState userState = UserState.ACTIVE;

    @Enumerated(EnumType.STRING)
    private UserRole userRole = UserRole.USER;

    private boolean lawAgreement = false;
    private boolean isVerified = false;

    @Convert(converter = StringListConverter.class)
    private List<Genre> genres = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Level level = Level.LEVEL_ONE;

    private String phoneNumber;

    @Builder
    private User (
            String nickname,
            List<Genre> genres,
            boolean lawAgreement,
            boolean isVerified,
            OauthInfo oauthInfo,
            Level level
            ){
        this.nickname = nickname;
        this.lawAgreement = lawAgreement;
        this.isVerified = isVerified;
        this.oauthInfo = oauthInfo;
        this.level = level;
        this.genres = genres;
    }

    public static User of(
            String nickname,
            List<Genre> genres,
            boolean lawAgreement,
            OauthInfo oauthInfo
    ){
        return User.builder()
                .nickname(nickname)
                .genres(genres)
                .lawAgreement(lawAgreement)
                .oauthInfo(oauthInfo)
                .build();
    }

    public void login() {
        if (!UserState.ACTIVE.equals(this.userState)) {
            throw ServerForbiddenException.EXCEPTION;
        }
    }

    public void validateUserStateNormal() {
        if (!this.userState.equals(UserState.ACTIVE)) {
            throw ServerForbiddenException.EXCEPTION;
        }
    }

    public void updateInfo(String name, List<Genre> genres) {
        if (!UserState.ACTIVE.equals(this.userState)) {
            throw ServerForbiddenException.EXCEPTION;
        }
        this.nickname = nickname;
        this.genres = genres;
    }


}
