package com.example.api.auth.service;

import com.example.adaptor.UseCase;
import com.example.api.auth.model.dto.response.OauthSignInResponse;
import com.example.api.auth.model.dto.response.OauthTokenResponse;
import com.example.api.auth.service.helper.OauthHelper;
import com.example.api.auth.service.helper.TokenGenerateHelper;
import com.example.domains.user.entity.User;
import com.example.domains.user.enums.OauthInfo;
import com.example.domains.user.enums.OauthProvider;
import com.example.domains.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Slf4j
@RequiredArgsConstructor
public class OauthLoginUseCase {
    private final OauthHelper oauthHelper;
    private final UserService userDomainService;
    private final TokenGenerateHelper tokenGenerateHelper;
    //private final SpringEnvironmentHelper springEnvironmentHelper;

    @Transactional
    public OauthSignInResponse loginWithCode(OauthProvider provider, String code, String referer) {
        final OauthTokenResponse oauthTokenResponse =
                oauthHelper.getCredential(provider, code, referer);
        return processLoginWithIdToken(provider, oauthTokenResponse.getIdToken());
    }

    @Transactional
    public OauthSignInResponse loginWithIdToken(OauthProvider provider, String idToken) {
        return processLoginWithIdToken(provider, idToken);
    }


    private OauthSignInResponse processLoginWithIdToken(OauthProvider provider, String idToken) {
        final OauthInfo oauthInfo = oauthHelper.getOauthInfo(provider, idToken);
        return checkUserCanLogin(oauthInfo, idToken);
    }

    @Transactional
    public OauthSignInResponse devLogin(OauthProvider provider, String code) {
        final OauthTokenResponse oauthTokenResponse = oauthHelper.getCredentialDev(provider, code);
        return processLoginWithIdTokenDev(provider, oauthTokenResponse.getIdToken());
    }

    private OauthSignInResponse processLoginWithIdTokenDev(OauthProvider provider, String idToken) {
        final OauthInfo oauthInfo = oauthHelper.getOauthInfoDev(provider, idToken);
        return checkUserCanLogin(oauthInfo, idToken);
    }


    private OauthSignInResponse checkUserCanLogin(OauthInfo oauthInfo, String idToken) {
        if (userDomainService.checkUserCanLogin(oauthInfo.getOid())) {
            User user = userDomainService.loginUser(oauthInfo);
            return tokenGenerateHelper.execute(user);
        } else {
            return OauthSignInResponse.cannotLogin(idToken);
        }
    }
}