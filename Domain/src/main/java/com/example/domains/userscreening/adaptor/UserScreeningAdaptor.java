package com.example.domains.userscreening.adaptor;

import com.example.adaptor.Adaptor;
import com.example.domains.screeningReview.entity.QScreeningReview;
import com.example.domains.screeningReview.entity.dto.ReviewResponseDto;
import com.example.domains.screeningReview.entity.dto.ScreeningReviewResponseDto;
import com.example.domains.user.entity.User;
import com.example.domains.user.repository.UserRepository;
import com.example.domains.userscreening.entity.QUserScreening;
import com.example.domains.userscreening.entity.UserScreening;
import com.example.domains.userscreening.exception.exceptions.UserScreeningNotFound;
import com.example.domains.userscreening.repository.UserScreeningRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Adaptor
@RequiredArgsConstructor
public class UserScreeningAdaptor {
    private final UserScreeningRepository userScreeningRepository;
    private final JPAQueryFactory queryFactory;

    public void save(UserScreening userScreening) {
        userScreeningRepository.save(userScreening);
    }

    public UserScreening findByUserAndScreening(Long userId, Long screeningId) {
        if (checkExists(userId,screeningId)) {
            return userScreeningRepository.findByUserIdAndScreeningId(userId,screeningId).get();
        } else {
            throw UserScreeningNotFound.EXCEPTION;
        }
    }



    
    public boolean checkExists(Long userId, Long screeningId) {
        Optional<UserScreening> userScreening = userScreeningRepository.findByUserIdAndScreeningId(userId,screeningId);
        if (userScreening.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public void updateUserScreening(UserScreening userScreening) {
        if(!userScreening.isBookmarked()){
            userScreening.updateBookmarkedAndParticipating(true,true);
        }else{
            userScreening.updateBookmarkedAndParticipating(false,true);
        }

    }

    public List<UserScreening> findByUserId(Long userId) {
        return queryFactory
                .selectFrom(QUserScreening.userScreening)
                .where(QUserScreening.userScreening.isHost.eq(true),QUserScreening.userScreening.user.id.eq(userId))
                .fetch();
    }


    @Transactional
    public List<ScreeningReviewResponseDto> getReviewListByScreening(Long userId, Long screeningId) {
        // Use QueryDSL to perform the projection
        List<ScreeningReviewResponseDto> reviewResponseDtos = queryFactory
                .select(Projections.constructor(
                        ScreeningReviewResponseDto.class,
                        QScreeningReview.screeningReview.afterScreening,
                        QScreeningReview.screeningReview.createdAt,
                        QScreeningReview.screeningReview.userScreening.screening.id,
                        QScreeningReview.screeningReview.review
                ))
                .from(QScreeningReview.screeningReview)
                .join(QUserScreening.userScreening)
                .on(QUserScreening.userScreening.id.eq(QScreeningReview.screeningReview.userScreening.id)
                        .and(QUserScreening.userScreening.isHost.eq(false))
                        .and(QUserScreening.userScreening.user.id.eq(userId)))
                .where(QUserScreening.userScreening.screening.id.eq(screeningId))
                .fetch();


        return reviewResponseDtos;
    }
}
