package com.example.api.user.service;

import com.example.adaptor.UseCase;
import com.example.api.config.security.SecurityUtil;
import com.example.domains.user.service.UserService;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class PatchUserMarketingStateUseCase {
    private final UserService userService;
    public void execute() {
        Long userId = SecurityUtil.getCurrentUserId();
        userService.turnMarketingState(userId);
    }
}
