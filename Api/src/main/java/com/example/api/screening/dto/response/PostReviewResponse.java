package com.example.api.screening.dto.response;

import com.example.domains.screeningReview.entity.ScreeningReview;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostReviewResponse {
    @Schema(defaultValue = "true", description = "기대 지수")
    private boolean beforeScreeningSatisfied;

    @Schema(defaultValue = "true", description = "만족도")
    private boolean afterScreening;

    @Schema(defaultValue = "true", description = "작품")
    private boolean screeningReview;

    @Schema(defaultValue = "true", description = "장소")
    private boolean locationReview;
    @Schema(defaultValue = "true", description = "운영")
    private boolean serviceReview;

    @Schema(defaultValue = "너무 좋았습니다", description = "텍스트 리뷰")
    private String review;

    @Schema(defaultValue = "true", description = "정책 약관")
    private boolean hasAgreed;

    @Schema(defaultValue = "1", description = "screening아이디")
    private Long screeningId;

    @Builder
    public PostReviewResponse(boolean beforeScreeningSatisfied, boolean afterScreening, boolean screeningReview,
                             boolean locationReview, boolean serviceReview, String review, boolean hasAgreed, Long screeningId) {
        this.beforeScreeningSatisfied = beforeScreeningSatisfied;
        this.afterScreening = afterScreening;
        this.screeningReview = screeningReview;
        this.locationReview = locationReview;
        this.serviceReview = serviceReview;
        this.review = review;
        this.hasAgreed =hasAgreed;
        this.screeningId = screeningId;
    }
    public static PostReviewResponse from(ScreeningReview screeningReview) {
        return PostReviewResponse.builder()
                .beforeScreeningSatisfied(screeningReview.isBeforeScreening())
                .afterScreening(screeningReview.isAfterScreening())
                .screeningReview(screeningReview.isMovieReview())
                .locationReview(screeningReview.isLocationReview())
                .serviceReview(screeningReview.isServiceReview())
                .review(screeningReview.getReview())
                .hasAgreed(screeningReview.isHasAgreed())
                .screeningId(screeningReview.getUserScreening().getScreening().getId())
                .build();
    }
}
