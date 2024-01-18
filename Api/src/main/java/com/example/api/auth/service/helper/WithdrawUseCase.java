package com.example.api.auth.service.helper;

import com.example.adaptor.UseCase;
import com.example.api.config.security.SecurityUtil;
import com.example.domains.quit.domain.Quit;
import com.example.domains.quit.domain.enums.Reason;
import com.example.domains.quit.service.QuitDomainService;
import com.example.domains.user.adaptor.UserAdaptor;
import com.example.domains.user.entity.User;
import com.example.domains.user.enums.OauthProvider;
import com.example.domains.user.service.UserService;
import com.example.error.exception.InvalidOauthProviderException;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class WithdrawUseCase {
    private final OauthHelper oauthHelper;
    private final UserAdaptor userAdaptor;
    private final QuitDomainService quitDomainService;
    private final UserService userService;
    @Transactional
    public void execute(String appleAccessToken, String referer, Long userId, Reason reason) {
        User user = userAdaptor.findById(userId);
        quitDomainService.save(Quit.of(userId, reason));
        // oauth쪽 탈퇴
        withdrawOauth(user.getOauthInfo().getProvider(), appleAccessToken, user, referer);
        // 우리쪽 탈퇴
        withdrawService(userId, user);
    }

    private void withdrawService(Long userId, User user) {
        userService.deleteUserById(userId);
    }

    @Transactional
    public void executeDev(String appleAccessToken) {
        Long userId = SecurityUtil.getCurrentUserId();
        User user = userAdaptor.findById(userId);
        // oauth쪽 탈퇴
        withdrawOauthDev(user.getOauthInfo().getProvider(), appleAccessToken, user);
        // 우리쪽 탈퇴
        withdrawService(userId, user);
    }

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
