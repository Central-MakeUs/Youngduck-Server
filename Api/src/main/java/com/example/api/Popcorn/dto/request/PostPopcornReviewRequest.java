package com.example.api.Popcorn.dto.request;

import com.example.domains.popcornUser.entity.enums.PopcornNegative;
import com.example.domains.popcornUser.entity.enums.PopcornPositive;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostPopcornReviewRequest {
    @Schema(defaultValue = "true", description = "관람 여부")
    private boolean hasWatched;
    @Schema(defaultValue = "true", description = "관람 전 만족도")
    private boolean beforeScreening;
    @Schema(defaultValue = "true", description = "관람 후 만족도")
    private boolean afterScreening;
    @Schema(defaultValue = "너무 좋았습니다", description = "텍스트 리뷰")
    private String review;

    @Schema(defaultValue = "true", description = "정책 약관")
    private boolean hasAgreed;

    @Schema(description = "Positive attributes")
    private PopcornPositive popcornPositive;
    @Schema(description = "Negative attributes")
    private PopcornNegative popcornNegative;

    @Builder
    public PostPopcornReviewRequest(boolean hasWatched, boolean beforeScreening, boolean afterScreening, String review, boolean hasAgreed, PopcornPositive popcornPositive, PopcornNegative popcornNegative) {
        this.hasWatched=hasWatched;
        this.beforeScreening = beforeScreening;
        this.afterScreening = afterScreening;
        this.review = review;
        this.hasAgreed =hasAgreed;
        this.popcornPositive = popcornPositive;
        this.popcornNegative = popcornNegative;
    }
}
