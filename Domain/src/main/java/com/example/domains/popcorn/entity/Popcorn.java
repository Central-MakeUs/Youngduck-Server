package com.example.domains.popcorn.entity;

import com.example.domains.common.model.BaseTimeEntity;
import com.example.domains.popcornReview.entity.PopcornReview;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Popcorn extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String movieId;

    private String movieTitle;
    private String imageUrl;
    private String movieDetail;
    private String recommendationReason;
    private int popcornRate;


    @Builder
    private Popcorn (String movieId,String movieTitle, String imageUrl, String movieDetail, String recommendationReason) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.imageUrl = imageUrl;
        this.movieDetail = movieDetail;
        this.recommendationReason = recommendationReason;
    }

    public static Popcorn of(String movieId,String movieTitle, String imageUrl, String movieDetail, String recommendationReason) {
        return Popcorn.builder()
                .movieId(movieId)
                .movieTitle(movieTitle)
                .imageUrl(imageUrl)
                .movieDetail(movieDetail)
                .recommendationReason(recommendationReason)
                .build();
    }
}