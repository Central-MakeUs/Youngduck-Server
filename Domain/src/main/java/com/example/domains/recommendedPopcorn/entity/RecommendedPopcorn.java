package com.example.domains.recommendedPopcorn.entity;

import com.example.domains.common.model.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecommendedPopcorn extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String movieId;

    private String movieTitle;
    private String imageUrl;
    private String movieDetail;
    private String recommendationReason;
    private int recommendationCount;

    @Builder
    private RecommendedPopcorn(String movieId, String movieTitle, String imageUrl, String  movieDetail, String recommendationReason) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.imageUrl = imageUrl;
        this.movieDetail = movieDetail;
        this.recommendationReason=recommendationReason;
    }

    public static RecommendedPopcorn of(String movieId, String movieTitle, String imageUrl, String  movieDetail, String recommendationReason) {
        return RecommendedPopcorn.builder()
                .movieId(movieId)
                .movieTitle(movieTitle)
                .imageUrl(imageUrl)
                .movieDetail(movieDetail)
                .recommendationReason(recommendationReason)
                .build();
    }
}
