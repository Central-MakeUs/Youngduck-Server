package com.example.api.screening.service;

import com.example.adaptor.UseCase;
import com.example.api.config.security.SecurityUtil;
import com.example.domains.screeningReview.entity.QScreeningReview;
import com.example.domains.screeningReview.entity.dto.ReviewResponseDto;
import com.example.domains.user.validator.UserValidator;
import com.example.domains.userscreening.adaptor.UserScreeningAdaptor;
import com.example.domains.userscreening.entity.UserScreening;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class GetReviewListUseCase {
    private final UserValidator userValidator;
    private final UserScreeningAdaptor userScreeningAdaptor;

    public List<ReviewResponseDto> execute(Long screeningId) {
        Long userId = SecurityUtil.getCurrentUserId();
        validateExecution(userId);

        List<ReviewResponseDto> reviewResponseDtoList = userScreeningAdaptor.getReviewListByScreening(userId,screeningId);
        return reviewResponseDtoList;
    }
    private void validateExecution(Long userId) {
        userValidator.validateUserStatusNormal(userId);
    }
}
