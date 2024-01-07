package com.example.api.auth.service.helper;

import com.example.adaptor.Helper;
import com.example.api.auth.model.dto.OauthUserInfoDto;
import com.example.domains.user.enums.OauthInfo;
import com.example.domains.user.enums.OauthProvider;
import com.example.dto.OIDCDecodePayload;
import com.example.oauth.client.KakaoInfoClient;
import com.example.oauth.client.KakaoOauthClient;
import com.example.oauth.dto.KakaoInfoResponseDto;
import com.example.oauth.dto.KakaoTokenResponse;
import com.example.oauth.dto.KakaoUnlinkTarget;
import com.example.oauth.dto.OIDCPublicKeysResponse;
import com.example.properties.KakaoOAuthProperties;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import static com.example.consts.PopCornMateConsts.BEARER;

@Helper
@Slf4j
@RequiredArgsConstructor
public class KakaoOauthHelper {
    private final KakaoOAuthProperties kakaoOauthProperties;
    private final KakaoInfoClient kakaoInfoClient;
    private final KakaoOauthClient kakaoOauthClient;
    private final OauthOIDCHelper oauthOIDCHelper;

    /** link * */
    public String getKaKaoOauthLink(String referer) {
        return kakaoOauthProperties.getBaseUrl()
                + String.format(
                "/oauth/authorize?client_id=%s&redirect_uri=%s&response_type=code",
                kakaoOauthProperties.getClientId(),
                referer + "kakao/callback");
    }

    public String getKaKaoOauthLinkDev() {
        return kakaoOauthProperties.getBaseUrl()
                + String.format(
                "/oauth/authorize?client_id=%s&redirect_uri=%s&response_type=code",
                kakaoOauthProperties.getWebClientId(),
                kakaoOauthProperties.getRedirectUrl());
    }

    /** token * */
    public KakaoTokenResponse getKakaoOauthToken(String code, String referer) {
        System.out.println(referer);
        return kakaoOauthClient.kakaoAuth(
                kakaoOauthProperties.getClientId(),
                "http://localhost:8080/kakao/callback",
                code,
                kakaoOauthProperties.getClientSecret());
    }

    public KakaoTokenResponse getKakaoOauthTokenDev(String code) {
        return kakaoOauthClient.kakaoAuth(
                kakaoOauthProperties.getWebClientId(),
                kakaoOauthProperties.getRedirectUrl(),
                code,
                kakaoOauthProperties.getClientSecret());
    }

    /** idtoken 분석 * */
    public OauthInfo getKakaoOauthInfoByIdToken(String idToken) {
        System.out.println(idToken);
        OIDCDecodePayload oidcDecodePayload = getOIDCDecodePayload(idToken);
        System.out.println(oidcDecodePayload.getSub());
        return OauthInfo.of(OauthProvider.KAKAO, oidcDecodePayload.getSub(),oidcDecodePayload.getEmail());
    }

    public OauthInfo getKakaoOauthInfoByIdTokenDev(String idToken) {
        OIDCDecodePayload oidcDecodePayload = getOIDCDecodePayloadDev(idToken);
        return OauthInfo.of(OauthProvider.KAKAO, oidcDecodePayload.getSub(),oidcDecodePayload.getEmail());
    }

    /** oidc decode * */
    public OIDCDecodePayload getOIDCDecodePayload(String token) {
        OIDCPublicKeysResponse oidcPublicKeysResponse = kakaoOauthClient.getKakaoOIDCOpenKeys();
        System.out.println(oidcPublicKeysResponse);
        return oauthOIDCHelper.getPayloadFromIdToken(
                token,
                kakaoOauthProperties.getBaseUrl(),
                kakaoOauthProperties.getAppId(),
                oidcPublicKeysResponse);
    }

    public OIDCDecodePayload getOIDCDecodePayloadDev(String token) {
        OIDCPublicKeysResponse oidcPublicKeysResponse = kakaoOauthClient.getKakaoOIDCOpenKeys();
        return oauthOIDCHelper.getPayloadFromIdToken(
                token,
                kakaoOauthProperties.getBaseUrl(),
                kakaoOauthProperties.getWebAppId(),
                oidcPublicKeysResponse);
    }

    /** kakao측 회원 탈퇴 * */
    public void withdrawKakaoOauthUser(String oid) {
        String kakaoAdminKey = kakaoOauthProperties.getAdminKey();
        KakaoUnlinkTarget unlinkKaKaoTarget = KakaoUnlinkTarget.from(oid);
        String header = "KakaoAK " + kakaoAdminKey;
        log.info(
                "{} {} {}",
                header,
                unlinkKaKaoTarget.getTargetIdType(),
                unlinkKaKaoTarget.getAud());
        kakaoInfoClient.unlinkUser(header, unlinkKaKaoTarget);
    }

    /** 유저 정보 가져오기 * */
    public OauthUserInfoDto getUserInfo(String oauthAccessToken) {
        KakaoInfoResponseDto response =
                kakaoInfoClient.kakaoUserInfo(BEARER + oauthAccessToken);
        return OauthUserInfoDto.fromKakao(response);
    }
}