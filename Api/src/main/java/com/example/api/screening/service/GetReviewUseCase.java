package com.example.api.screening.service;

import com.example.adaptor.UseCase;
import com.example.api.config.security.SecurityUtil;
import com.example.api.screening.dto.response.PostReviewResponse;
import com.example.domains.screening.entity.Screening;
import com.example.domains.screeningReview.adaptor.ReviewAdaptor;
import com.example.domains.screeningReview.entity.ScreeningReview;
import com.example.domains.user.validator.UserValidator;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class GetReviewUseCase {
    private final UserValidator userValidator;
    private final ReviewAdaptor reviewAdaptor;
    public PostReviewResponse execute(Long reviewId) {
        Long userId = SecurityUtil.getCurrentUserId();
        validateExecution(userId);
        ScreeningReview screeningReview = reviewAdaptor.findById(reviewId);
        return PostReviewResponse.from(screeningReview);
    }
    private void validateExecution(Long userId) {
        userValidator.validateUserStatusNormal(userId);
    }
}
