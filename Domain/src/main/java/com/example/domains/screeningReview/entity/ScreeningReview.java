package com.example.domains.screeningReview.entity;

import com.example.domains.common.model.BaseTimeEntity;
import com.example.domains.screening.entity.Screening;
import com.example.domains.user.entity.User;
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

    private boolean beforeScreening;
    private boolean afterScreening;
    private boolean movieReview;
    private boolean locationReview;
    private boolean serviceReview;
    private boolean hasAgreed;

    @OneToOne
    @JoinColumn(name = "user_screening_id")
    private UserScreening userScreening;

    @Builder
    private ScreeningReview(boolean beforeScreening,boolean afterScreening, boolean movieReview, boolean locationReview,boolean serviceReview, String review, boolean hasAgreed,UserScreening userScreening) {
        this.beforeScreening = beforeScreening;
        this.afterScreening = afterScreening;
        this.movieReview = movieReview;
        this.locationReview = locationReview;
        this.serviceReview = serviceReview;
        this.review = review;
        this.hasAgreed =hasAgreed;
        this.userScreening = userScreening;
    }

    public static ScreeningReview of(boolean beforeScreening,boolean afterScreening, boolean movieReview, boolean locationReview,boolean serviceReview, String review, boolean hasAgreed,UserScreening userScreening) {
        return ScreeningReview.builder()
                .beforeScreening(beforeScreening)
                .afterScreening(afterScreening)
                .movieReview(movieReview)
                .locationReview(locationReview)
                .serviceReview(serviceReview)
                .review(review)
                .hasAgreed(hasAgreed)
                .userScreening(userScreening)
                .build();

    }
}
