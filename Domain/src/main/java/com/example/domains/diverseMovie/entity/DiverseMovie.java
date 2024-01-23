package com.example.domains.diverseMovie.entity;

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
public class DiverseMovie extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String movieTitle;
    private String movieImgUrl;
    private String movieRank;

    @Builder
    public DiverseMovie(String movieTitle,String movieRank) {
        this.movieTitle=movieTitle;
        this.movieRank = movieRank;
    }
    public static DiverseMovie of(String movieTitle,String  movieRank){
        return DiverseMovie.builder()
                .movieTitle(movieTitle)
                .movieRank(movieRank)
                .build();
    }
}
