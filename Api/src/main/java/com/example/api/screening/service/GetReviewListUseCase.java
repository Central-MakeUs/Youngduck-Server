package com.example.api.screening.service;

import com.example.adaptor.UseCase;
import com.example.api.screening.dto.response.ScreeningReviewUserResponse;
import com.example.domains.screeningReview.entity.dto.ScreeningReviewResponseDto;
import com.example.domains.user.validator.UserValidator;
import com.example.domains.userscreening.adaptor.UserScreeningAdaptor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@UseCase
@RequiredArgsConstructor
public class GetReviewListUseCase {
    private final UserValidator userValidator;
    private final UserScreeningAdaptor userScreeningAdaptor;

    public List<ScreeningReviewUserResponse> execute(Long screeningId) {
        List<ScreeningReviewResponseDto> reviewResponseDtoList = userScreeningAdaptor.getReviewListByScreening(screeningId);

        List<ScreeningReviewUserResponse> result = proceed(reviewResponseDtoList);

        return result;
    }

    private List<ScreeningReviewUserResponse> proceed(List<ScreeningReviewResponseDto> reviewResponseDtoList) {
        List<ScreeningReviewUserResponse> screeningReviewResponseDtoList = new ArrayList<>();

        for (ScreeningReviewResponseDto reviewResponseDto : reviewResponseDtoList) {
            screeningReviewResponseDtoList.add(ScreeningReviewUserResponse.from(reviewResponseDto));
        }
        return screeningReviewResponseDtoList;
    }

    private void validateExecution(Long userId) {
        userValidator.validateUserStatusNormal(userId);
    }
}
