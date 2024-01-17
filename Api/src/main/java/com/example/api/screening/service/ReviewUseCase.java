package com.example.api.screening.service;

import com.example.adaptor.UseCase;
import com.example.api.config.security.SecurityUtil;
import com.example.api.screening.dto.request.PostReviewRequest;
import com.example.api.screening.dto.response.PostReviewResponse;
import com.example.domains.screening.adaptor.ScreeningAdaptor;
import com.example.domains.screening.entity.Screening;
import com.example.domains.screening.validator.ScreeningValidator;
import com.example.domains.screeningReview.adaptor.ReviewAdaptor;
import com.example.domains.screeningReview.entity.ScreeningReview;
import com.example.domains.screeningReview.validator.ReviewValidator;
import com.example.domains.user.adaptor.UserAdaptor;
import com.example.domains.user.entity.User;
import com.example.domains.user.validator.UserValidator;
import com.example.domains.userscreening.adaptor.UserScreeningAdaptor;
import com.example.domains.userscreening.entity.UserScreening;
import com.example.domains.userscreening.exception.exceptions.UserScreeningIsHost;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class ReviewUseCase {
    private final UserValidator userValidator;

    private final ReviewValidator reviewValidator;
    private final UserAdaptor userAdaptor;
    private final ScreeningAdaptor screeningAdaptor;
    private final ScreeningValidator screeningValidator;
    private final UserScreeningAdaptor userScreeningAdaptor;
    private final ReviewAdaptor reviewAdaptor;

    public PostReviewResponse execute(Long screenId, PostReviewRequest request) {
        Long userId = SecurityUtil.getCurrentUserId();
        UserScreening userScreening = validate(screenId,userId);
        return reviewUpload(userScreening,request);
        //save, userScreenId
    }

    private PostReviewResponse reviewUpload(UserScreening userScreening,PostReviewRequest request) {
        final ScreeningReview newSreeningReview = ScreeningReview.of(
                request.isBeforeScreeningSatisfied(),
                request.isAfterScreening(),
                request.isScreeningReview(),
                request.isLocationReview(),
                request.isServiceReview(),
                request.getReview(),
                request.isHasAgreed(),
                userScreening
        );
        //save
        reviewAdaptor.save(newSreeningReview);
        return PostReviewResponse.from(newSreeningReview);
    }

    private UserScreening validate(Long screenId, Long userId){
        //userid, screeningid를 가지고
        User user = userAdaptor.findById(userId);
        Screening screening = screeningAdaptor.findById(screenId);
        //userScreening가져오기

        //실제로 존재하는지
        userScreeningAdaptor.checkExists(userId,screenId);
        //userScreeningValidator에서 주최자인지 아닌지 판단, 주최자면 error, 아니면 proceed
        //존재하는 찜까지 되어있는 상태인지
        UserScreening userScreening = userScreeningAdaptor.findByUserAndScreening(userId,screenId);
        validateUserScreening(userScreening);

        //스크리닝에서 날짜 지났는지 여부를 validator에서 걸러주기 - screeningvalidator
        screeningValidator.checkDateValidation(screening);
        //UserScreening id반환하기
        return userScreening;
    }

    private void validateUserScreening(UserScreening userScreening) {
        //주최자인지 판단 (isHost)
        //존재하는 찜까지 되어있는 상태인지
        if (userScreening.isHost() || !userScreening.isBookmarked()) {
            throw UserScreeningIsHost.EXCEPTION;
        }

    }


    private void validateExecution(Long userId) {
        userValidator.validateUserStatusNormal(userId);
    }
}
