package com.example.api.screening.dto.request;

import com.example.domains.screeningReview.entity.enums.Negative;
import com.example.domains.screeningReview.entity.enums.Positive;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostReviewRequest {

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

    @Schema(description = "Positive attributes")
    private Positive positive;

    @Schema(description = "Negative attributes")
    private Negative negative;


    @Builder
    public PostReviewRequest(boolean afterScreening, boolean screeningReview,
                             boolean locationReview, boolean serviceReview, String review, boolean hasAgreed,
                             Positive positive, Negative negative) {
        this.afterScreening = afterScreening;
        this.screeningReview = screeningReview;
        this.locationReview = locationReview;
        this.serviceReview = serviceReview;
        this.review = review;
        this.hasAgreed = hasAgreed;
        this.positive = positive;
        this.negative = negative;
    }
}
