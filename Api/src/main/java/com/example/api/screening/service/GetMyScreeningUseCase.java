package com.example.api.screening.service;

import com.example.adaptor.UseCase;
import com.example.api.config.security.SecurityUtil;
import com.example.api.screening.dto.response.ScreeningResponse;
import com.example.domains.screening.adaptor.ScreeningAdaptor;
import com.example.domains.screening.entity.Screening;
import com.example.domains.user.validator.UserValidator;
import com.example.domains.userscreening.adaptor.UserScreeningAdaptor;
import com.example.domains.userscreening.entity.UserScreening;
import com.example.domains.userscreening.exception.exceptions.UserScreeningIsNotHost;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class GetMyScreeningUseCase {
    private final UserValidator userValidator;
    private final ScreeningAdaptor screeningAdaptor;
    private final UserScreeningAdaptor userScreeningAdaptor;

    public ScreeningResponse execute(Long screeningId) {
        Long userId = SecurityUtil.getCurrentUserId();
        validateExecution(userId);
        UserScreening userScreening = userScreeningAdaptor.findByUserAndScreening(userId,screeningId);
        Screening screening = screeningAdaptor.findById(screeningId);
        if (!userScreening.isHost()) {
            throw UserScreeningIsNotHost.EXCEPTION;
        }
        return ScreeningResponse.from(screening);
    }

    private void validateExecution(Long userId) {
        userValidator.validateUserStatusNormal(userId);
    }

}
