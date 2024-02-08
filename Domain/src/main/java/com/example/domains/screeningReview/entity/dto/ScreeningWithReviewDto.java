package com.example.domains.screeningReview.entity.dto;

import com.example.domains.screeningReview.entity.ScreeningReview;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
public class ScreeningWithReviewDto {
    private String hostName;

    private boolean afterScreening;


    private boolean screeningReview;


    private boolean locationReview;

    private boolean serviceReview;


    private String review;


    private boolean hasAgreed;

    private Long screeningId;
    private String screeningTitle;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String posterImgUrl;

    @Builder
    @QueryProjection
    public ScreeningWithReviewDto (String hostName, boolean afterScreening, boolean screeningReview,
                             boolean locationReview, boolean serviceReview, String review, boolean hasAgreed, Long screeningId,
                             String screeningTitle,LocalDateTime startDate, LocalDateTime endDate, String posterImgUrl) {
        this.hostName = hostName;
        this.afterScreening = afterScreening;
        this.screeningReview = screeningReview;
        this.locationReview = locationReview;
        this.serviceReview = serviceReview;
        this.review = review;
        this.hasAgreed =hasAgreed;
        this.screeningId = screeningId;
        this.screeningTitle = screeningTitle;
        this.startDate = startDate;
        this.endDate =endDate;
        this.posterImgUrl  = posterImgUrl ;
    }
    public static ScreeningWithReviewDto  from(ScreeningReview screeningReview) {
        return  ScreeningWithReviewDto.builder()
                .hostName(screeningReview.getUserScreening().getScreening().getHostInfo().getHostName())
                .afterScreening(screeningReview.isAfterScreening())
                .screeningReview(screeningReview.isMovieReview())
                .locationReview(screeningReview.isLocationReview())
                .serviceReview(screeningReview.isServiceReview())
                .review(screeningReview.getReview())
                .hasAgreed(screeningReview.isHasAgreed())
                .screeningId(screeningReview.getUserScreening().getScreening().getId())
                .screeningTitle(screeningReview.getUserScreening().getScreening().getTitle())
                .startDate(screeningReview.getUserScreening().getScreening().getScreeningStartDate())
                .endDate(screeningReview.getUserScreening().getScreening().getScreeningEndDate())
                .posterImgUrl(screeningReview.getUserScreening().getScreening().getPosterImgUrl())
                .build();
    }
}
