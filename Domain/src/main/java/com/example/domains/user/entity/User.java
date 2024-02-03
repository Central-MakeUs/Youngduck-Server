package com.example.domains.user.entity;

import com.example.domains.common.StringListConverter;
import com.example.domains.common.model.BaseTimeEntity;
import com.example.domains.user.enums.*;
import com.example.domains.user.exception.exceptions.AlreadyDeletedUserException;
import com.example.error.exception.ServerForbiddenException;
import com.example.fcm.entity.FCMToken;
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

    private int profileImgNum;

//    private String phoneNumber;
    private String appleEmail;

    @OneToOne(mappedBy = "user")
    private FCMToken fcmToken;

    private boolean marketingAgreement;

    @Builder
    private User (
            String nickname,
            boolean lawAgreement,
            boolean marketingAgreement,
            boolean isVerified,
            String appleEmail,
            String name,
            OauthInfo oauthInfo,
            List<Genre> genres,
            int profileImgNum
            ){
        this.nickname = nickname;
        this.lawAgreement = lawAgreement;
        this.marketingAgreement = marketingAgreement;
        this.isVerified = isVerified;
        this.oauthInfo = oauthInfo;
        this.appleEmail = appleEmail;
        this.name = name;
        this.genres = genres;
        this.profileImgNum =profileImgNum;
    }

    public static User of(
            String nickname,
            boolean lawAgreement,
            boolean marketingAgreement,
            List<Genre> genres,
            String appleEmail,
            String name,
            OauthInfo oauthInfo,
            int profileImgNum
    ){
        return User.builder()
                .nickname(nickname)
                .lawAgreement(lawAgreement)
                .marketingAgreement(marketingAgreement)
                .genres(genres)
                .appleEmail(appleEmail)
                .name(name)
                .oauthInfo(oauthInfo)
                .profileImgNum(profileImgNum)
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

    public void updateInfo(String name) {
        if (!UserState.ACTIVE.equals(this.userState)) {
            throw ServerForbiddenException.EXCEPTION;
        }
        this.nickname = name;
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

    public void turnBlind() {
        this.userState = UserState.DELETED;
    }

    public void turnmarketingAgreement(boolean state) {
        this.marketingAgreement = !state;
    }
}
