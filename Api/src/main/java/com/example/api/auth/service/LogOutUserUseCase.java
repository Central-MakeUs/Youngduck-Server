package com.example.api.auth.service;

import com.example.adaptor.UseCase;
import com.example.api.config.security.SecurityUtil;
import com.example.domains.user.adaptor.RefreshTokenAdaptor;
import com.example.fcm.adaptor.FcmTokenAdaptor;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class LogOutUserUseCase {
    private final RefreshTokenAdaptor refreshTokenAdaptor;
    private final FcmTokenAdaptor fcmTokenAdaptor;

    @Transactional
    public void execute() {
        Long userId = SecurityUtil.getCurrentUserId();
        deleteToken(userId);
        refreshTokenAdaptor.deleteTokenByUserId(userId);
    }


    private void deleteToken(Long userId) {
        fcmTokenAdaptor.execute(userId);
    }
}