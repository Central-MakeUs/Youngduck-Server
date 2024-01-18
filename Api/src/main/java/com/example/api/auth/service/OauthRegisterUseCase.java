package com.example.api.auth.service;

import com.example.adaptor.UseCase;
import com.example.api.auth.model.dto.OauthUserInfoDto;
import com.example.api.auth.model.dto.request.RegisterRequest;
import com.example.api.auth.model.dto.response.OauthRegisterResponse;
import com.example.api.auth.service.helper.OauthHelper;
import com.example.api.auth.service.helper.TokenGenerateHelper;
import com.example.domains.user.entity.User;
import com.example.domains.user.enums.OauthInfo;
import com.example.domains.user.enums.OauthProvider;
import com.example.domains.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class OauthRegisterUseCase {
    private final OauthHelper oauthHelper;
    private final UserService userDomainService;
    private final TokenGenerateHelper tokenGenerateHelper;

    @Transactional
    public OauthRegisterResponse execute(
            OauthProvider provider,
            String idToken,
            RegisterRequest request) {
        final OauthInfo oauthInfo = oauthHelper.getOauthInfo(provider, idToken);
        //final OauthUserInfoDto oauthUserInfoDto = getUserInfo(provider, oauthAccessToken);
        final User user = registerUser(provider, oauthInfo, request);
        return OauthRegisterResponse.from(tokenGenerateHelper.execute(user));
    }

    @Transactional
    public OauthRegisterResponse executeDev(
            OauthProvider provider,
            String idToken,
            RegisterRequest request) {
        final OauthInfo oauthInfo = oauthHelper.getOauthInfoDev(provider, idToken);
        final User user = registerUser(provider, oauthInfo, request);
        return OauthRegisterResponse.from(tokenGenerateHelper.execute(user));
    }


    //TODO 랜덤 id부여
    private User registerUser(
            OauthProvider provider,
            OauthInfo oauthInfo,
            RegisterRequest request) {
        switch (provider) {
            case APPLE:
                return userDomainService.registerUser(
                        request.getNickname(),
                        request.getGenres(),
                        request.isLawAgreement(),
                        request.getEmail(),
                        request.getName(),
                        oauthInfo);
            default:
                return userDomainService.registerUser(
                        request.getNickname(),
                        request.getGenres(),
                        request.isLawAgreement(),
                        "",
                        request.getName(),
                        oauthInfo);
        }
    }

    private OauthUserInfoDto getUserInfo(OauthProvider provider, String oauthAccessToken) {
        switch (provider) {
            case APPLE:
                return null;
            default:
                return oauthHelper.getUserInfo(provider, oauthAccessToken);
        }
    }
}