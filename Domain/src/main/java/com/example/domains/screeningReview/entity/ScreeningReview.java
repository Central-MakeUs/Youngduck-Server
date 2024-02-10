package com.example.domains.screeningReview.entity;

import com.example.domains.common.model.BaseTimeEntity;
import com.example.domains.screeningReview.entity.enums.Negative;
import com.example.domains.screeningReview.entity.enums.Positive;
import com.example.domains.userscreening.entity.UserScreening;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScreeningReview extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String review;

    private int likes;
    private boolean afterScreening;
    private boolean movieReview;
    private boolean locationReview;
    private boolean serviceReview;
    private boolean hasAgreed;
    private int complaintCount;

    private boolean isBlind;

    @Embedded
    private Positive positive;

    @Embedded
    private Negative negative;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_screening_id")
    private UserScreening userScreening;

    @Builder
    private ScreeningReview(boolean afterScreening, boolean movieReview, boolean locationReview,boolean serviceReview, String review, boolean hasAgreed,UserScreening userScreening,Positive positive,Negative negative) {
        this.afterScreening = afterScreening;
        this.movieReview = movieReview;
        this.locationReview = locationReview;
        this.serviceReview = serviceReview;
        this.review = review;
        this.hasAgreed =hasAgreed;
        this.userScreening = userScreening;
        this.positive = positive;
        this.negative =negative;
    }

    public static ScreeningReview of(boolean afterScreening, boolean movieReview, boolean locationReview,boolean serviceReview, String review, boolean hasAgreed,UserScreening userScreening,Positive positive, Negative negative) {
        return ScreeningReview.builder()
                .afterScreening(afterScreening)
                .movieReview(movieReview)
                .locationReview(locationReview)
                .serviceReview(serviceReview)
                .review(review)
                .hasAgreed(hasAgreed)
                .userScreening(userScreening)
                .positive(positive)
                .negative(negative)
                .build();

    }
}
