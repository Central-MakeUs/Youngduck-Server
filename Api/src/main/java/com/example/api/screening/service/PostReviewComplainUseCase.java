package com.example.api.screening.service;

import com.example.adaptor.UseCase;
import com.example.api.config.security.SecurityUtil;
import com.example.domains.screeningReview.adaptor.ReviewAdaptor;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class PostReviewComplainUseCase {
    private final ReviewAdaptor reviewAdaptor;
    public void execute(Long reviewId) {
        Long userId = SecurityUtil.getCurrentUserId();
        reviewAdaptor.postComplain(reviewId,userId);
    }
}
