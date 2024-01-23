package com.example.domains.screeningReview.adaptor;

import com.example.adaptor.Adaptor;
import com.example.domains.block.adaptor.BlockAdaptor;
import com.example.domains.screening.entity.QScreening;
import com.example.domains.screeningReview.entity.QScreeningReview;
import com.example.domains.screeningReview.entity.ScreeningReview;
import com.example.domains.screeningReview.entity.dto.ReviewResponseDto;
import com.example.domains.screeningReview.entity.dto.ScreeningWithReviewDto;
import com.example.domains.screeningReview.repository.ScreeningReviewRepository;
import com.example.domains.user.entity.QUser;
import com.example.domains.user.entity.User;
import com.example.domains.user.enums.UserState;
import com.example.domains.userscreening.entity.QUserScreening;
import com.example.domains.userscreening.entity.UserScreening;
import com.google.api.client.util.SecurityUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Adaptor
@RequiredArgsConstructor
public class ReviewAdaptor {
    private final ScreeningReviewRepository screeningReviewRepository;
    private final BlockAdaptor blockAdaptor;
    private final JPAQueryFactory queryFactory;

    public void save(ScreeningReview screeningReview) {
        screeningReviewRepository.save(screeningReview);
    }

    public ScreeningReview findById(Long reviewId) {
        return screeningReviewRepository.findById(reviewId).get();
    }

    //본인이 리뷰한 (host아님) 상영회에 대한 리뷰 반환
    public List<ScreeningWithReviewDto> getPostedScreeningReviews(Long userId) {
        // QUserScreening과 QScreeningReview는 QueryDSL에서 생성된 엔터티 클래스여야 합니다.

        // QUserScreening과 QScreeningReview 간의 조인을 수행하고, 호스트가 아닌 것 및 특정 사용자에 대한 필터링을 수행합니다.
        List<ScreeningWithReviewDto> screeningReviews = queryFactory
                .select(
                        Projections.constructor(
                                ScreeningWithReviewDto.class,
                                QScreeningReview.screeningReview.afterScreening,
                                QScreeningReview.screeningReview.movieReview,
                                QScreeningReview.screeningReview.locationReview,
                                QScreeningReview.screeningReview.serviceReview,
                                QScreeningReview.screeningReview.review,
                                QScreeningReview.screeningReview.hasAgreed,
                                QScreeningReview.screeningReview.userScreening.screening.id,
                                QScreeningReview.screeningReview.userScreening.screening.title,// Include profileImg
                                QScreeningReview.screeningReview.userScreening.screening.screeningStartDate, // Include startDate
                                QScreeningReview.screeningReview.userScreening.screening.screeningEndDate, // Include endDate,
                                QScreeningReview.screeningReview.userScreening.screening.posterImgUrl
                ))
                .from(QScreeningReview.screeningReview)
                .join(QUserScreening.userScreening)
                .on(
                                        QUserScreening.userScreening.id.eq(QScreeningReview.screeningReview.userScreening.id),
                                        QUserScreening.userScreening.isHost.eq(false),
                                        QUserScreening.userScreening.user.id.eq(userId),
                                        QScreeningReview.screeningReview.userScreening.screening.isPrivate.eq(false)
                )
                .fetch();

        return screeningReviews;
    }

    @Transactional
    public void postComplain(Long reviewId,Long userId) {
        ScreeningReview screeningReview = findById(reviewId);

        blockAdaptor.save(userId,screeningReview.getUserScreening().getUser().getId(),reviewId);

        int complainCount = screeningReview.getComplaintCount();
        if (complainCount == 4) {
            incrementComplaintCount(screeningReview);
            // Get user from the screeningReview
            User user = screeningReview.getUserScreening().getUser();

            deActivateUser(user);

            // Delete the screeningReview
            changeBlindStatus(screeningReview);  // Assuming there is a method to delete screeningReview
        } else {
            incrementComplaintCount(screeningReview);
        }
    }

    @Transactional
    public void deActivateUser(User user) {
        user.turnBlind();
    }

    @Transactional
    public void changeBlindStatus(ScreeningReview screeningReview) {
        QScreeningReview qScreeningReview = QScreeningReview.screeningReview;
        JPAUpdateClause updateClause = queryFactory.update(qScreeningReview);

//        // Set blindStatus to true
        updateClause
                .set(qScreeningReview.isBlind, true)
                .where(qScreeningReview.id.eq(screeningReview.getId()))
                .execute();

    }

    @Transactional
    public void incrementComplaintCount(ScreeningReview screeningReview) {
        QScreeningReview qScreeningReview = QScreeningReview.screeningReview;
        JPAUpdateClause updateClause = queryFactory.update(qScreeningReview);

//        // Set blindStatus to true
        updateClause
                .set(qScreeningReview.complaintCount, qScreeningReview.complaintCount.add(1))
                .where(qScreeningReview.id.eq(screeningReview.getId()))
                .execute();
    }

}
