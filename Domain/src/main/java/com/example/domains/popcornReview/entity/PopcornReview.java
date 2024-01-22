package com.example.domains.popcornReview.entity;

import com.example.domains.common.model.BaseTimeEntity;
import com.example.domains.popcorn.entity.Popcorn;
import com.example.domains.popcornReview.entity.enums.PopcornNegative;
import com.example.domains.popcornReview.entity.enums.PopcornPositive;
import com.example.domains.popcornUser.entity.PopcornUser;
import com.example.domains.screeningReview.entity.ScreeningReview;
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
public class PopcornReview extends BaseTimeEntity {
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
    private PopcornPositive popcornPositive;

    @Embedded
    private PopcornNegative popcornNegative;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "popcorn_user_id")
    private PopcornUser popcornUser;


    @Builder
    private PopcornReview(boolean afterScreening, boolean movieReview, boolean locationReview,boolean serviceReview, String review, boolean hasAgreed,PopcornPositive popcornPositive,PopcornNegative popcornNegative,PopcornUser popcornUser) {
        this.afterScreening = afterScreening;
        this.movieReview = movieReview;
        this.locationReview = locationReview;
        this.serviceReview = serviceReview;
        this.review = review;
        this.hasAgreed =hasAgreed;
        this.popcornPositive = popcornPositive;
        this.popcornNegative = popcornNegative;
        this.popcornUser = popcornUser;
    }

    public static  PopcornReview of(boolean afterScreening, boolean movieReview, boolean locationReview, boolean serviceReview, String review, boolean hasAgreed,PopcornPositive popcornPositive,PopcornNegative popcornNegative,PopcornUser popcornUser) {
        return PopcornReview.builder()
                .afterScreening(afterScreening)
                .movieReview(movieReview)
                .locationReview(locationReview)
                .serviceReview(serviceReview)
                .review(review)
                .hasAgreed(hasAgreed)
                .popcornPositive(popcornPositive)
                .popcornNegative(popcornNegative)
                .popcornUser(popcornUser)
                .build();

    }
}
