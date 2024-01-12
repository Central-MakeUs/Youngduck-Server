package com.example.api.auth.service.helper;

import com.example.adaptor.UseCase;
import com.example.domains.user.entity.User;
import com.example.domains.user.enums.OauthProvider;
import com.example.error.exception.InvalidOauthProviderException;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class WithdrawUseCase {
    private final OauthHelper oauthHelper;

    private void withdrawOauth(
            OauthProvider provider, String appleAccessToken, User user, String referer) {
        switch (provider) {
            case KAKAO -> oauthHelper.withdraw(provider, user.getOauthInfo().getOid(), null, null);
            case APPLE -> oauthHelper.withdraw(provider, null, appleAccessToken, referer);
            default -> throw InvalidOauthProviderException.EXCEPTION;
        }
    }

    private void withdrawOauthDev(OauthProvider provider, String appleAccessToken, User user) {
        switch (provider) {
            case KAKAO -> oauthHelper.withdrawDev(provider, user.getOauthInfo().getOid(), null);
            case APPLE -> oauthHelper.withdrawDev(provider, null, appleAccessToken);
            default -> throw InvalidOauthProviderException.EXCEPTION;
        }
    }
}
