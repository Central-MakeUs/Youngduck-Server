package com.example.domains.screeningReview.adaptor;

import com.example.adaptor.Adaptor;
import com.example.domains.screeningReview.entity.QScreeningReview;
import com.example.domains.screeningReview.entity.ScreeningReview;
import com.example.domains.screeningReview.entity.dto.ReviewResponseDto;
import com.example.domains.screeningReview.repository.ScreeningReviewRepository;
import com.example.domains.userscreening.entity.QUserScreening;
import com.example.domains.userscreening.entity.UserScreening;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Adaptor
@RequiredArgsConstructor
public class ReviewAdaptor {
    private final ScreeningReviewRepository screeningReviewRepository;
    private final JPAQueryFactory queryFactory;

    public void save(ScreeningReview screeningReview) {
        screeningReviewRepository.save(screeningReview);
    }

    //본인이 리뷰한 (host아님) 상영회에 대한 리뷰 반환
    public List<ReviewResponseDto> getPostedScreeningReviews(Long userId) {
        // QUserScreening과 QScreeningReview는 QueryDSL에서 생성된 엔터티 클래스여야 합니다.

        // QUserScreening과 QScreeningReview 간의 조인을 수행하고, 호스트가 아닌 것 및 특정 사용자에 대한 필터링을 수행합니다.
        List<ReviewResponseDto> screeningReviews = queryFactory
                .select(
                        Projections.constructor(
                                ReviewResponseDto.class,
                                QScreeningReview.screeningReview.beforeScreening,
                                QScreeningReview.screeningReview.afterScreening,
                                QScreeningReview.screeningReview.movieReview,
                                QScreeningReview.screeningReview.locationReview,
                                QScreeningReview.screeningReview.serviceReview,
                                QScreeningReview.screeningReview.review,
                                QScreeningReview.screeningReview.hasAgreed,
                                QScreeningReview.screeningReview.userScreening.screening.id
                        )
                )
                .from(QScreeningReview.screeningReview)
                .join(QUserScreening.userScreening)
                .on(QUserScreening.userScreening.id.eq(QScreeningReview.screeningReview.userScreening.id),
                        QUserScreening.userScreening.isHost.eq(false),
                        QUserScreening.userScreening.user.id.eq(userId))
                .fetch();

        return screeningReviews;
    }
}
