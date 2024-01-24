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

    @Column(columnDefinition = "TEXT")
    private String movieDetail;
    private String recommendationReason;
    private int popcornRate;
    private String directorName;
    private int recommendationCount;


    @Builder
    private Popcorn (String movieId,String movieTitle, String imageUrl, String movieDetail,String directorName, String recommendationReason,int recommendationCount) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.imageUrl = imageUrl;
        this.movieDetail = movieDetail;
        this.directorName = directorName;
        this.recommendationReason = recommendationReason;
        this.recommendationCount = recommendationCount;
    }

    public static Popcorn of(String movieId,String movieTitle, String imageUrl, String movieDetail, String directorName, String recommendationReason,int recommendationCount) {
        return Popcorn.builder()
                .movieId(movieId)
                .movieTitle(movieTitle)
                .imageUrl(imageUrl)
                .movieDetail(movieDetail)
                .directorName(directorName)
                .recommendationReason(recommendationReason)
                .recommendationCount(recommendationCount)
                .build();
    }
}
