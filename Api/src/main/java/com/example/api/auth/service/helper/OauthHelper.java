package com.example.api.auth.service.helper;

import com.example.adaptor.Helper;
import com.example.api.auth.model.dto.OauthUserInfoDto;
import com.example.api.auth.model.dto.response.OauthLoginLinkResponse;
import com.example.api.auth.model.dto.response.OauthTokenResponse;
import com.example.domains.user.enums.OauthInfo;
import com.example.domains.user.enums.OauthProvider;
import com.example.error.exception.InvalidOauthProviderException;
import lombok.RequiredArgsConstructor;


@Helper
@RequiredArgsConstructor
public class OauthHelper {
    private final KakaoOauthHelper kakaoOauthHelper;
    private final AppleOauthHelper appleOauthHelper;

    /** oauth link 가져오기 * */
    public OauthLoginLinkResponse getOauthLinkDev(OauthProvider provider) {
        return switch (provider) {
            case KAKAO -> new OauthLoginLinkResponse(kakaoOauthHelper.getKaKaoOauthLinkDev());
            default -> throw InvalidOauthProviderException.EXCEPTION;
        };
    }

    public OauthLoginLinkResponse getOauthLink(OauthProvider provider, String referer) {
        return switch (provider) {
            case KAKAO -> new OauthLoginLinkResponse(kakaoOauthHelper.getKaKaoOauthLink(referer));
            default -> throw InvalidOauthProviderException.EXCEPTION;
        };
    }

    /** idtoken 가져오기 * */
    public OauthTokenResponse getCredential(OauthProvider provider, String code, String referer) {
        return switch (provider) {
            case KAKAO -> OauthTokenResponse.from(
                    kakaoOauthHelper.getKakaoOauthToken(code, referer));
            case APPLE -> OauthTokenResponse.from(
                    appleOauthHelper.getAppleOAuthToken(code, referer));
            default -> throw InvalidOauthProviderException.EXCEPTION;
        };
    }

    public OauthTokenResponse getCredentialDev(OauthProvider provider, String code) {
        return switch (provider) {
            case KAKAO -> OauthTokenResponse.from(kakaoOauthHelper.getKakaoOauthTokenDev(code));
            case APPLE -> OauthTokenResponse.from(appleOauthHelper.getAppleOAuthTokenDev(code));
            default -> throw InvalidOauthProviderException.EXCEPTION;
        };
    }

    /** idtoken 분석 * */
    public OauthInfo getOauthInfo(OauthProvider provider, String idToken) {
        return switch (provider) {
            case KAKAO -> kakaoOauthHelper.getKakaoOauthInfoByIdToken(idToken);
            case APPLE -> appleOauthHelper.getAppleOAuthInfoByIdToken(idToken);
            default -> throw InvalidOauthProviderException.EXCEPTION;
        };
    }

    public OauthInfo getOauthInfoDev(OauthProvider provider, String idToken) {
        return switch (provider) {
            case KAKAO -> kakaoOauthHelper.getKakaoOauthInfoByIdTokenDev(idToken);
            case APPLE -> appleOauthHelper.getAppleOAuthInfoByIdToken(idToken);
            default -> throw InvalidOauthProviderException.EXCEPTION;
        };
    }

    /** 회원탈퇴 * */
    public void withdraw(
            OauthProvider provider, String oid, String appleAccessToken, String referer) {
        switch (provider) {
            case KAKAO -> kakaoOauthHelper.withdrawKakaoOauthUser(oid);
            case APPLE -> appleOauthHelper.withdrawAppleOauthUser(appleAccessToken, referer);
            default -> throw InvalidOauthProviderException.EXCEPTION;
        }
    }


    /** 유저 정보 가져오기 * */
    public OauthUserInfoDto getUserInfo(OauthProvider provider, String oauthAccessToken) {
        return switch (provider) {
            case KAKAO -> kakaoOauthHelper.getUserInfo(oauthAccessToken);
            default -> throw InvalidOauthProviderException.EXCEPTION;
        };
    }


}