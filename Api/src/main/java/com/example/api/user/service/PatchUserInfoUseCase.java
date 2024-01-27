package com.example.api.user.service;

import com.example.adaptor.UseCase;
import com.example.api.config.security.SecurityUtil;
import com.example.api.user.model.dto.UpdateUserInfoRequest;
import com.example.domains.user.adaptor.UserAdaptor;
import com.example.domains.user.entity.User;
import com.example.domains.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class PatchUserInfoUseCase {
    private final UserService userService;

    @Transactional
    public void execute(UpdateUserInfoRequest request) {
        Long userId = SecurityUtil.getCurrentUserId();

        userService.updateUserById(userId,request.getNickname());
    }
}
