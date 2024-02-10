package com.example.api.user.service;


import com.example.adaptor.UseCase;
import com.example.api.config.security.SecurityUtil;
import com.example.api.user.model.dto.GetUserInfoResponse;
import com.example.domains.user.adaptor.UserAdaptor;
import com.example.domains.user.entity.User;
import com.example.domains.user.service.UserService;
import com.example.domains.user.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class GetUserInfoUseCase {
    private final UserAdaptor userAdaptor;
    private final UserValidator userValidator;
    private final UserService userService;

    @Transactional(readOnly = true)
    public GetUserInfoResponse execute() {
        Long userId = SecurityUtil.getCurrentUserId();
        validateExecution(userId);
        User user = userAdaptor.findById(userId);
        return GetUserInfoResponse.from(user);
    }

    private void validateExecution(Long userId) {
        userValidator.validateUserStatusNormal(userId);
    }
}