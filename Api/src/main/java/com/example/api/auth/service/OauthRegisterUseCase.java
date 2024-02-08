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

import java.util.Random;

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
        int profileImgNumber = makeRandomNumber();
        final User user = registerUser(provider, oauthInfo, request,profileImgNumber);
        return OauthRegisterResponse.from(tokenGenerateHelper.execute(user));
    }

    private int makeRandomNumber() {
        Random random = new Random();

        int randomNumber = random.nextInt(3) + 1;

        return randomNumber;
    }

    @Transactional
    public OauthRegisterResponse executeDev(
            OauthProvider provider,
            String idToken,
            RegisterRequest request) {
        final OauthInfo oauthInfo = oauthHelper.getOauthInfoDev(provider, idToken);
        int profileImgNumber = makeRandomNumber();
        final User user = registerUser(provider, oauthInfo, request,profileImgNumber);
        return OauthRegisterResponse.from(tokenGenerateHelper.execute(user));
    }


    //TODO 랜덤 id부여
    private User registerUser(
            OauthProvider provider,
            OauthInfo oauthInfo,
            RegisterRequest request,
            int profileImgNumer) {
        switch (provider) {
            case APPLE:
                return userDomainService.registerUser(
                        request.getNickname(),
                        request.isLawAgreement(),
                        request.isMarketingAgreement(),
                        request.getGenres(),
                        request.getEmail(),
                        request.getName(),
                        oauthInfo,
                        profileImgNumer);
            default:
                return userDomainService.registerUser(
                        request.getNickname(),
                        request.isLawAgreement(),
                        request.isMarketingAgreement(),
                        request.getGenres(),
                        "",
                        request.getName(),
                        oauthInfo,
                        profileImgNumer
                        );
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