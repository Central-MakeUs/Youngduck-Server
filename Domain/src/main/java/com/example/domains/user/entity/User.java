package com.example.domains.user.entity;

import com.example.domains.common.StringListConverter;
import com.example.domains.common.model.BaseTimeEntity;
import com.example.domains.user.enums.*;
import com.example.domains.user.exception.exceptions.AlreadyDeletedUserException;
import com.example.error.exception.ServerForbiddenException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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

    //TODO :조금 더 생각하기
    @Convert(converter = StringListConverter.class)
    private List<Genre> genres = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Level level = Level.ONE;

//    private String phoneNumber;
    private String appleEmail;

    @Builder
    private User (
            String nickname,
            List<Genre> genres,
            boolean lawAgreement,
            boolean isVerified,
            String appleEmail,
            String name,
            OauthInfo oauthInfo
            ){
        this.nickname = nickname;
        this.lawAgreement = lawAgreement;
        this.isVerified = isVerified;
        this.oauthInfo = oauthInfo;
        this.appleEmail = appleEmail;
        this.name = name;
        this.genres = genres;
    }

    public static User of(
            String nickname,
            List<Genre> genres,
            boolean lawAgreement,
            String appleEmail,
            String name,
            OauthInfo oauthInfo

    ){
        return User.builder()
                .nickname(nickname)
                .genres(genres)
                .lawAgreement(lawAgreement)
                .appleEmail(appleEmail)
                .name(name)
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


    public void checkOidDuplicate(String oid) {
        if (this.oauthInfo.getOid().equals(oid)) {
            throw new IllegalArgumentException("already Exists");
        }
    }

    public void withdrawUser() {
        if (UserState.DELETED.equals(this.userState)) {
            throw AlreadyDeletedUserException.EXCEPTION;
        }
        this.userState = UserState.DELETED;
        this.nickname = LocalDateTime.now() + "삭제한 유저";
        this.oauthInfo.withDrawOauthInfo();
        this.genres = new ArrayList<>();
        this.name = null;
    }
}
