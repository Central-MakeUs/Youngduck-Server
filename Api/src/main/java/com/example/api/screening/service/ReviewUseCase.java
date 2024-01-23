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
import com.example.domains.screeningReview.entity.enums.Negative;
import com.example.domains.screeningReview.entity.enums.Positive;
import com.example.domains.screeningReview.validator.ReviewValidator;
import com.example.domains.user.adaptor.UserAdaptor;
import com.example.domains.user.entity.User;
import com.example.domains.user.validator.UserValidator;
import com.example.domains.userscreening.adaptor.UserScreeningAdaptor;
import com.example.domains.userscreening.entity.UserScreening;
import com.example.domains.userscreening.exception.exceptions.UserScreeningIsHost;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

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
                request.isAfterScreening(),
                request.isScreeningReview(),
                request.isLocationReview(),
                request.isServiceReview(),
                request.getReview(),
                request.isHasAgreed(),
                userScreening,
                request.getPositive(),
                request.getNegative()
        );
        Screening screening = screeningAdaptor.findById(userScreening.getScreening().getId());

        incrementNegative(request.getNegative(),screening);
        incrementPositive(request.getPositive(),screening);

        

        //save
        reviewAdaptor.save(newSreeningReview);
        //screeningAdaptor.save(screening);
        return PostReviewResponse.from(newSreeningReview);
    }

//    @Transactional
//    public PostReviewResponse proceed(ScreeningReview newSreeningReview, Screening screening) {
//        screeningAdaptor.save(screening);
//        return PostReviewResponse.from(newSreeningReview);
//    }
//

    @Transactional
    public void incrementPositive(Positive positive, Screening userScreening) {
        if (positive != null) {
            if (positive.isCineMaster()) {
                screeningAdaptor.incrementPositiveCineMaster(userScreening);
            }
            if (positive.isGreatFilming()) {
                screeningAdaptor.incrementPositiveGreatFilming(userScreening);
            }
            if (positive.isPom()) {
                screeningAdaptor.incrementPositivePom(userScreening);
            }
            if (positive.isAnimationIsGood()) {
                screeningAdaptor.incrementPositiveAnimationIsGood(userScreening);
            }
            if (positive.isArtIsGood()) {
                screeningAdaptor.incrementPositiveArtIsGood(userScreening);
            }
            if (positive.isCustom()) {
                screeningAdaptor.incrementPositiveCustom(userScreening);
            }
            if (positive.isMusic()) {
                screeningAdaptor.incrementPositiveMusic(userScreening);
            }
            if (positive.isTopicIsGood()) {
                screeningAdaptor.incrementPositiveTopicIsGood(userScreening);
            }
            if (positive.isLinesAreGood()) {
                screeningAdaptor.incrementPositiveLinesAreGood(userScreening);
            }
            if (positive.isEndingIsGood()) {
                screeningAdaptor.incrementPositiveEndingIsGood(userScreening);
            }
            if (positive.isCastingIsGood()) {
                screeningAdaptor.incrementPositiveCastingIsGood(userScreening);
            }
            if (positive.isActingIsGood()) {
                screeningAdaptor.incrementPositiveActingIsGood(userScreening);
            }
            if (positive.isChemistryIsGood()) {
                screeningAdaptor.incrementPositiveChemistryIsGood(userScreening);
            }
            // Add similar checks for other positive attributes
        }

    }


    @Transactional
    public void incrementNegative(Negative negative,Screening userScreening) {

        if ( negative != null) {
            if ( negative.isIffy()) {
                screeningAdaptor.incrementNegativeIffy(userScreening);
            }
            if ( negative.isBadEditing()) {
                screeningAdaptor.incrementNegativeBadEditing(userScreening);
            }
            if ( negative.isBadAngle()) {
                screeningAdaptor.incrementNegativeBadAngle(userScreening);
            }
            if ( negative.isBadDetail()) {
                screeningAdaptor.incrementNegativeBadDetail(userScreening);
            }
            if ( negative.isBadColor()) {
                screeningAdaptor.incrementNegativeBadColor(userScreening);
            }
            if ( negative.isBadCustom()) {
                screeningAdaptor.incrementNegativeBadCustom(userScreening);
            }
            if ( negative.isBadMusic()) {
                screeningAdaptor.incrementNegativeBadMusic(userScreening);
            }
            if ( negative.isBadSound()) {
                screeningAdaptor.incrementNegativeBadSound(userScreening);
            }
            if ( negative.isBadEnding()) {
                screeningAdaptor.incrementNegativeBadEnding(userScreening);
            }
            if ( negative.isEndingLoose()) {
                screeningAdaptor.incrementNegativeEndingLoose(userScreening);
            }
            if ( negative.isNoDetail()) {
                screeningAdaptor.incrementNegativeNoDetail(userScreening);
            }
            if ( negative.isBadTopic()) {
                screeningAdaptor.incrementNegativeBadTopic(userScreening);
            }
            if ( negative.isBadActing()) {
                screeningAdaptor.incrementNegativeBadActing(userScreening);
            }
            if ( negative.isBadCasting()) {
                screeningAdaptor.incrementNegativeBadCasting(userScreening);
            }
            // Add similar checks for other negative attributes
        }

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
