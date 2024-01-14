package com.example.api.screening.service;

import com.example.adaptor.UseCase;
import com.example.api.config.security.SecurityUtil;
import com.example.api.screening.dto.response.ScreeningUploadResponse;
import com.example.domains.screening.adaptor.ScreeningAdaptor;
import com.example.domains.screening.entity.Screening;
import com.example.domains.user.validator.UserValidator;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class GetScreeningUseCase {
    private final UserValidator userValidator;
    private final ScreeningAdaptor screeningAdaptor;

    public Screening execute(Long screeningId) {
        Long userId = SecurityUtil.getCurrentUserId();
        validateExecution(userId);
        Screening screening = screeningAdaptor.findById(screeningId);
        return screening;
    }

    private void validateExecution(Long userId) {
        userValidator.validateUserStatusNormal(userId);
    }
}
