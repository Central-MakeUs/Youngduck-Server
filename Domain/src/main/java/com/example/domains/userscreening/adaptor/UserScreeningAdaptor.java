package com.example.domains.userscreening.adaptor;

import com.example.adaptor.Adaptor;
import com.example.domains.screening.entity.Screening;
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

import static com.example.domains.screening.entity.QScreening.screening;
import static com.example.domains.userscreening.entity.QUserScreening.userScreening;

@Adaptor
@RequiredArgsConstructor
public class UserScreeningAdaptor {
    private final UserScreeningRepository userScreeningRepository;
    private final JPAQueryFactory queryFactory;

    public List<UserScreening> findByBookMarked() {
        List<UserScreening> bookmarkedUserScreenings = userScreeningRepository.findByIsBookmarked(true);
        return bookmarkedUserScreenings;
    }

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
                .selectFrom(userScreening)
                .where(userScreening.isHost.eq(true), userScreening.user.id.eq(userId))
                .fetch();
    }

    public List<Screening> findBookmarkedUserScreening(Long userId) {
        return queryFactory
                .select(screening)
                .from(screening)
                .join(userScreening).on(userScreening.screening.eq(screening)) // Join with Screening entity
                .where(
                        userScreening.isHost.eq(false),
                        userScreening.isBookmarked.eq(true),
                        userScreening.user.id.eq(userId)
                )
                .fetch();
    }



    @Transactional
    public List<ScreeningReviewResponseDto> getReviewListByScreening(Long userId, Long screeningId) {
        // Use QueryDSL to perform the projection
        List<ScreeningReviewResponseDto> reviewResponseDtos = queryFactory
                .select(Projections.constructor(
                        ScreeningReviewResponseDto.class,
                        QScreeningReview.screeningReview.id,
                        QScreeningReview.screeningReview.afterScreening,
                        QScreeningReview.screeningReview.createdAt,
                        QScreeningReview.screeningReview.userScreening.screening.id,
                        QScreeningReview.screeningReview.review
                ))
                .from(QScreeningReview.screeningReview)
                .join(userScreening)
                .on(userScreening.id.eq(QScreeningReview.screeningReview.userScreening.id)
                        .and(userScreening.isHost.eq(false))
                        .and(userScreening.user.id.eq(userId)))
                .where(userScreening.screening.id.eq(screeningId))
                .fetch();


        return reviewResponseDtos;
    }

    public List<UserScreening> findByScreeningId(Long screeningId) {
        return userScreeningRepository.findByScreeningId(screeningId);
    }
}
