package com.example.api.auth.service.helper;

import com.example.adaptor.Helper;
import com.example.domains.user.enums.OauthInfo;
import com.example.domains.user.enums.OauthProvider;
import com.example.dto.OIDCDecodePayload;
import com.example.error.exception.NoAppleCodeException;
import com.example.oauth.apple.AppleTokenResponse;
import com.example.oauth.apple.client.AppleOAuthClient;
import com.example.oauth.apple.client.AppleOIDCClient;
import com.example.oauth.apple.helper.AppleLoginUtil;
import com.example.oauth.dto.OIDCPublicKeysResponse;
import com.example.properties.AppleOAuthProperties;
import lombok.RequiredArgsConstructor;

import static com.example.consts.PopCornMateConsts.APPLE_OAUTH_QUERY_STRING;

@Helper
@RequiredArgsConstructor
public class AppleOauthHelper {
    private final AppleOAuthProperties appleOAuthProperties;
    private final AppleOAuthClient appleOAuthClient;
    private final AppleOIDCClient appleOIDCClient;
    private final OauthOIDCHelper oAuthOIDCHelper;

    /** Link * */
    public String getAppleOAuthLink(String referer) {
        return appleOAuthProperties.getBaseUrl()
                + String.format(
                APPLE_OAUTH_QUERY_STRING,
                appleOAuthProperties.getClientId(),
                referer + appleOAuthProperties.getWebCallbackUrl());
    }

    public String getAppleOauthLinkDev() {
        return appleOAuthProperties.getBaseUrl()
                + String.format(
                APPLE_OAUTH_QUERY_STRING,
                appleOAuthProperties.getWebClientId(),
                appleOAuthProperties.getRedirectUrl());
    }

    /** token * */
    public AppleTokenResponse getAppleOAuthToken(String code, String referer) {
        return appleOAuthClient.appleAuth(
                appleOAuthProperties.getClientId(),
                referer + appleOAuthProperties.getWebCallbackUrl(),
                code,
                this.getClientSecret());
    }

    public AppleTokenResponse getAppleOAuthTokenDev(String code) {
        return appleOAuthClient.appleAuth(
                appleOAuthProperties.getWebClientId(),
                appleOAuthProperties.getRedirectUrl(),
                code,
                this.getClientSecretDev());
    }

    /** idtoken 분석 * */
    public OauthInfo getAppleOAuthInfoByIdToken(String idToken) {
        OIDCDecodePayload oidcDecodePayload = this.getOIDCDecodePayload(idToken);
        return OauthInfo.builder()
                .provider(OauthProvider.APPLE)
                .oid(oidcDecodePayload.getSub())
                .build();
    }

    public OauthInfo getAppleOAuthInfoByIdTokenDev(String idToken) {
        OIDCDecodePayload oidcDecodePayload = this.getOIDCDecodePayloadDev(idToken);
        return OauthInfo.builder()
                .provider(OauthProvider.APPLE)
                .oid(oidcDecodePayload.getSub())
                .build();
    }

    /** oidc decode * */
    public OIDCDecodePayload getOIDCDecodePayload(String token) {
        OIDCPublicKeysResponse oidcPublicKeysResponse = appleOIDCClient.getAppleOIDCOpenKeys();
        return oAuthOIDCHelper.getPayloadFromIdToken(
                token,
                appleOAuthProperties.getBaseUrl(),
                appleOAuthProperties.getClientId(),
                oidcPublicKeysResponse);
    }

    public OIDCDecodePayload getOIDCDecodePayloadDev(String token) {
        OIDCPublicKeysResponse oidcPublicKeysResponse = appleOIDCClient.getAppleOIDCOpenKeys();
        return oAuthOIDCHelper.getPayloadFromIdToken(
                token,
                appleOAuthProperties.getBaseUrl(),
                appleOAuthProperties.getWebClientId(),
                oidcPublicKeysResponse);
    }

    /** apple측 회원 탈퇴 * */
    public void withdrawAppleOauthUser(String code, String referer) {
        if (code == null) {
            throw NoAppleCodeException.EXCEPTION;
        }
        AppleTokenResponse appleTokenResponse = getAppleOAuthToken(code, referer);
        appleOAuthClient.revoke(
                appleOAuthProperties.getClientId(),
                appleTokenResponse.getAccessToken(),
                this.getClientSecret());
    }

    public void withdrawAppleOauthUserDev(String code) {
        if (code == null) {
            throw NoAppleCodeException.EXCEPTION;
        }
        AppleTokenResponse appleTokenResponse = getAppleOAuthTokenDev(code);
        appleOAuthClient.revoke(
                appleOAuthProperties.getWebClientId(),
                appleTokenResponse.getAccessToken(),
                this.getClientSecret());
    }

    /** client secret 가져오기 * */
    private String getClientSecret() {
        return AppleLoginUtil.createClientSecret(
                appleOAuthProperties.getTeamId(),
                appleOAuthProperties.getClientId(),
                appleOAuthProperties.getKeyId(),
                appleOAuthProperties.getAuthKey(),
                appleOAuthProperties.getBaseUrl());
    }

    private String getClientSecretDev() {
        return AppleLoginUtil.createClientSecret(
                appleOAuthProperties.getTeamId(),
                appleOAuthProperties.getWebClientId(),
                appleOAuthProperties.getKeyId(),
                appleOAuthProperties.getAuthKey(),
                appleOAuthProperties.getBaseUrl());
    }
}