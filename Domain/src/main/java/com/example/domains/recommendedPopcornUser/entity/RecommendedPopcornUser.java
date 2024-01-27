package com.example.domains.recommendedPopcornUser.entity;

import com.example.domains.recommendedPopcorn.entity.RecommendedPopcorn;
import com.example.domains.screening.entity.Screening;
import com.example.domains.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecommendedPopcornUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean isAgreed;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "recommended_popcorn_id")
    private RecommendedPopcorn recommendedPopcorn;
    @Builder
    private RecommendedPopcornUser(boolean isAgreed, User user, RecommendedPopcorn recommendedPopcorn) {
        this.isAgreed = isAgreed;
        this.user = user;
        this.recommendedPopcorn = recommendedPopcorn;
    }
    public static RecommendedPopcornUser of(boolean isAgreed, User user, RecommendedPopcorn recommendedPopcorn) {
        return RecommendedPopcornUser.builder()
                .isAgreed(isAgreed)
                .user(user)
                .recommendedPopcorn(recommendedPopcorn)
                .build();
    }
}
