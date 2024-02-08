package com.example.api.screening.service;

import com.example.adaptor.UseCase;
import com.example.api.config.security.SecurityUtil;
import com.example.api.screening.dto.response.ScreeningInfoResponse;
import com.example.api.screening.dto.response.ScreeningResponse;
import com.example.domains.screening.adaptor.ScreeningAdaptor;
import com.example.domains.screening.entity.Screening;
import com.example.domains.screeningReview.adaptor.ReviewAdaptor;
import com.example.domains.user.validator.UserValidator;
import com.example.domains.userscreening.adaptor.UserScreeningAdaptor;
import com.example.domains.userscreening.entity.UserScreening;
import com.example.domains.userscreening.exception.exceptions.UserScreeningIsNotHost;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class GetScreeningUseCase {
    private final UserValidator userValidator;
    private final ScreeningAdaptor screeningAdaptor;
    private final UserScreeningAdaptor userScreeningAdaptor;
    private final ReviewAdaptor screeningReviewAdaptor;
    boolean isReviewed = false;
    boolean isBookMarked = false;

    public ScreeningInfoResponse execute(Long screeningId) {
        Long userId = SecurityUtil.getCurrentUserId();
        validateExecution(userId);
        Screening screening = screeningAdaptor.findById(screeningId);

        if(!validateUserScreening(userId,screeningId)){
            isReviewed = false;
            isBookMarked = false;
        } else {
            UserScreening userScreening = userScreeningAdaptor.findByUserAndScreening(userId,screeningId);
            isReviewed = validateScreeningReview(userScreening.getId());
            isBookMarked = userScreening.isBookmarked();
        };

        return ScreeningInfoResponse.from(screening,isBookMarked,isReviewed);
    }


    private boolean validateUserScreening(Long userId, Long screeningId) {
        return userScreeningAdaptor.existsByUserAndScreening(userId,screeningId);
    }

    private boolean validateScreeningReview(Long userScreeningId) {
        return screeningReviewAdaptor.checkIfExists(userScreeningId);
    }

    private void validateExecution(Long userId) {
        userValidator.validateUserStatusNormal(userId);
    }

    public ScreeningResponse getMyScreening(Long screeningId) {
        Long userId = SecurityUtil.getCurrentUserId();
        validateExecution(userId);
        UserScreening userScreening = userScreeningAdaptor.findByUserAndScreening(userId,screeningId);
        Screening screening = screeningAdaptor.findById(screeningId);
        if (!userScreening.isHost()) {
            throw UserScreeningIsNotHost.EXCEPTION;
        }
        return ScreeningResponse.from(screening);
    }
}
