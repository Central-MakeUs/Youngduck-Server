package com.example.api.Popcorn.service;

import com.example.adaptor.UseCase;
import com.example.api.Popcorn.dto.request.PostPopcornReviewRequest;
import com.example.api.Popcorn.dto.response.PopcornReviewResponse;
import com.example.api.config.security.SecurityUtil;
import com.example.domains.popcorn.adaptor.PopcornAdaptor;
import com.example.domains.popcorn.entity.Popcorn;
import com.example.domains.popcornUser.adaptor.PopcornUserAdaptor;
import com.example.domains.popcornUser.entity.PopcornUser;
import com.example.domains.popcornUser.entity.enums.PopcornNegative;
import com.example.domains.popcornUser.entity.enums.PopcornPositive;
import com.example.domains.popcornUser.exceptions.NoPopcornReview;
import com.example.domains.user.adaptor.UserAdaptor;
import com.example.domains.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class PostPopcornReviewUseCase {

    private final UserAdaptor userAdaptor;
    private final PopcornUserAdaptor popcornUserAdaptor;
    private final PopcornAdaptor popcornAdaptor;

    public PopcornReviewResponse execute(Long popcornId, PostPopcornReviewRequest request) {
        Long userId = SecurityUtil.getCurrentUserId();
        User user = userAdaptor.findById(userId);
        validatePopcorn(popcornId);
        Popcorn popcorn = popcornAdaptor.findById(popcornId);
        validateDuplicate(user,popcorn);
        return reviewUpload(popcorn,user,request);
        //save, userScreenId
    }

    private void validatePopcorn(Long id) {
        if(!popcornAdaptor.checkIfExists(id).isPresent()){
            throw NoPopcornReview.EXCEPTION;
        };
    }

    private void validateDuplicate(User user, Popcorn popcorn) {
        popcornUserAdaptor.checkIfExists(user.getId(),popcorn.getId());
    }


    private PopcornReviewResponse reviewUpload(Popcorn popcorn, User user, PostPopcornReviewRequest request) {
        final PopcornUser newPopcornUser = PopcornUser.of(
                request.isHasWatched(),
                request.isBeforeScreening(),
                request.isAfterScreening(),
                request.getReview(),
                request.isHasAgreed(),
                request.getPopcornPositive(),
                request.getPopcornNegative(),
                user,
                popcorn
        );

        calculateRate(request.isAfterScreening(),request.isBeforeScreening(),popcorn);
        incrementNegative(request.getPopcornNegative(),popcorn);
        incrementPositive(request.getPopcornPositive(),popcorn);

        //save
        popcornUserAdaptor.save(newPopcornUser);
        //screeningAdaptor.save(screening);
        return PopcornReviewResponse.from(newPopcornUser);
    }
    @Transactional
    public void calculateRate(boolean afterScreening,boolean beforeScreening,Popcorn popcorn) {
        if(afterScreening == true && beforeScreening == true) {
            popcornUserAdaptor.incrementPopcornRate(popcorn,100);
        } else if(afterScreening == true && beforeScreening == false) {
            popcornUserAdaptor.incrementPopcornRate(popcorn,25);
        } else if(afterScreening == false && beforeScreening == true) {
            popcornUserAdaptor.incrementPopcornRate(popcorn,75);
        } else {
            popcornUserAdaptor.incrementPopcornRate(popcorn,0);
        }
    }
    //TODO 더 좋은 방법이 없을까?
    @Transactional
    public void incrementPositive(PopcornPositive positive, Popcorn popcorn) {
        if (positive != null) {
            if (positive.isCineMaster()) {
                popcornAdaptor.incrementPositiveCineMaster(popcorn);
            }
            if (positive.isGreatFilming()) {
                popcornAdaptor.incrementPositiveGreatFilming(popcorn);
            }
            if (positive.isPom()) {
                popcornAdaptor.incrementPositivePom(popcorn);
            }
            if (positive.isAnimationIsGood()) {
                popcornAdaptor.incrementPositiveAnimationIsGood(popcorn);
            }
            if (positive.isArtIsGood()) {
                popcornAdaptor.incrementPositiveArtIsGood(popcorn);
            }
            if (positive.isSetIsArt()) {
                popcornAdaptor.incrementPositiveSetIsArt(popcorn);
            }
            if (positive.isCustom()) {
                popcornAdaptor.incrementPositiveCustom(popcorn);
            }
            if (positive.isMusic()) {
                popcornAdaptor.incrementPositiveMusic(popcorn);
            }
            if (positive.isOst()) {
                popcornAdaptor.incrementPositiveOst(popcorn);
            }
            if (positive.isWrittenByGod()) {
                popcornAdaptor.incrementPositiveWrittenByGod(popcorn);
            }
            if (positive.isTopicIsGood()) {
                popcornAdaptor.incrementPositiveTopicIsGood(popcorn);
            }
            if (positive.isLinesAreGood()) {
                popcornAdaptor.incrementPositiveLinesAreGood(popcorn);
            }
            if (positive.isEndingIsGood()) {
                popcornAdaptor.incrementPositiveEndingIsGood(popcorn);
            }
            if (positive.isCastingIsGood()) {
                popcornAdaptor.incrementPositiveCastingIsGood(popcorn);
            }
            if (positive.isActingIsGood()) {
                popcornAdaptor.incrementPositiveActingIsGood(popcorn);
            }
            if (positive.isChemistryIsGood()) {
                popcornAdaptor.incrementPositiveChemistryIsGood(popcorn);
            }
            // Add similar checks for other positive attributes
        }

    }


    @Transactional
    public void incrementNegative(PopcornNegative negative, Popcorn popcorn) {

        if ( negative != null) {
            if ( negative.isIffy()) {
                popcornAdaptor.incrementNegativeIffy(popcorn);
            }
            if ( negative.isBadEditing()) {
                popcornAdaptor.incrementNegativeBadEditing(popcorn);
            }
            if ( negative.isBadAngle()) {
                popcornAdaptor.incrementNegativeBadAngle(popcorn);
            }
            if ( negative.isBadDetail()) {
                popcornAdaptor.incrementNegativeBadDetail(popcorn);
            }
            if ( negative.isBadColor()) {
                popcornAdaptor.incrementNegativeBadColor(popcorn);
            }
            if ( negative.isBadCustom()) {
                popcornAdaptor.incrementNegativeBadCustom(popcorn);
            }
            if ( negative.isBadMusic()) {
                popcornAdaptor.incrementNegativeBadMusic(popcorn);
            }
            if ( negative.isBadSound()) {
                popcornAdaptor.incrementNegativeBadSound(popcorn);
            }
            if ( negative.isBadEnding()) {
                popcornAdaptor.incrementNegativeBadEnding(popcorn);
            }
            if ( negative.isEndingLoose()) {
                popcornAdaptor.incrementNegativeEndingLoose(popcorn);
            }
            if ( negative.isNoDetail()) {
                popcornAdaptor.incrementNegativeNoDetail(popcorn);
            }
            if ( negative.isBadTopic()) {
                popcornAdaptor.incrementNegativeBadTopic(popcorn);
            }
            if ( negative.isBadActing()) {
                popcornAdaptor.incrementNegativeBadActing(popcorn);
            }
            if ( negative.isBadCasting()) {
                popcornAdaptor.incrementNegativeBadCasting(popcorn);
            }
        }

    }

}
