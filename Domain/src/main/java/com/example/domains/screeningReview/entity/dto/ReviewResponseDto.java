package com.example.domains.screeningReview.entity.dto;

import com.example.domains.screeningReview.entity.ScreeningReview;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReviewResponseDto {

    private boolean beforeScreeningSatisfied;


    private boolean afterScreening;


    private boolean screeningReview;


    private boolean locationReview;

    private boolean serviceReview;


    private String review;


    private boolean hasAgreed;

    private Long screeningId;

    @Builder
    public ReviewResponseDto(boolean beforeScreeningSatisfied, boolean afterScreening, boolean screeningReview,
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
    public static ReviewResponseDto from(ScreeningReview screeningReview) {
        return ReviewResponseDto.builder()
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
