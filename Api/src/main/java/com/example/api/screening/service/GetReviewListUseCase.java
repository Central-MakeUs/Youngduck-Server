package com.example.api.screening.service;

import com.example.adaptor.UseCase;
import com.example.api.config.security.SecurityUtil;
import com.example.api.screening.dto.response.ScreeningReviewUserResponse;
import com.example.domains.screeningReview.entity.QScreeningReview;
import com.example.domains.screeningReview.entity.dto.ReviewResponseDto;
import com.example.domains.screeningReview.entity.dto.ScreeningReviewResponseDto;
import com.example.domains.screeningReview.entity.dto.ScreeningReviewUserResponseDto;
import com.example.domains.user.adaptor.UserAdaptor;
import com.example.domains.user.entity.User;
import com.example.domains.user.validator.UserValidator;
import com.example.domains.userscreening.adaptor.UserScreeningAdaptor;
import com.example.domains.userscreening.entity.UserScreening;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;

import java.util.ArrayList;
import java.util.List;

@UseCase
@RequiredArgsConstructor
public class GetReviewListUseCase {
    private final UserValidator userValidator;
    private final UserScreeningAdaptor userScreeningAdaptor;
    private final UserAdaptor userAdaptor;

    public List<ScreeningReviewUserResponse> execute(Long screeningId) {
        Long userId = SecurityUtil.getCurrentUserId();
        validateExecution(userId);

        List<ScreeningReviewResponseDto> reviewResponseDtoList = userScreeningAdaptor.getReviewListByScreening(userId,screeningId);
        User user = userAdaptor.findById(userId);

        List<ScreeningReviewUserResponse> result = proceed(reviewResponseDtoList,user);


        return result;
    }

    private List<ScreeningReviewUserResponse> proceed(List<ScreeningReviewResponseDto> reviewResponseDtoList, User user) {
        List<ScreeningReviewUserResponse> screeningReviewResponseDtoList = new ArrayList<>();

        for (ScreeningReviewResponseDto reviewResponseDto : reviewResponseDtoList) {
            //System.out.println(reviewResponseDto.getReview());
            screeningReviewResponseDtoList.add(ScreeningReviewUserResponse.from(reviewResponseDto,user));
        }
        //System.out.println(screeningReviewResponseDtoList.get(0).isAfterScreening());
        return screeningReviewResponseDtoList;
    }

    private void validateExecution(Long userId) {
        userValidator.validateUserStatusNormal(userId);
    }
}
