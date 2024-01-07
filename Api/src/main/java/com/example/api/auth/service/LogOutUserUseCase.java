package com.example.api.auth.service;

import com.example.adaptor.UseCase;
import com.example.api.config.security.SecurityUtil;
import com.example.domains.user.adaptor.RefreshTokenAdaptor;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class LogOutUserUseCase {
    private final RefreshTokenAdaptor refreshTokenAdaptor;

    @Transactional
    public void execute() {
        Long userId = SecurityUtil.getCurrentUserId();
        refreshTokenAdaptor.deleteTokenByUserId(userId);
    }
}