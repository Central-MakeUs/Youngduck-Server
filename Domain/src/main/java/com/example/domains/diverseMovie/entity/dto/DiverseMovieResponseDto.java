package com.example.domains.diverseMovie.entity.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DiverseMovieResponseDto {
    private Long movieId;
    private String movieTitle;
    private String movieImgUrl;
    private String movieRank;

    @QueryProjection
    public DiverseMovieResponseDto (Long movieId,String movieTitle, String movieImgUrl, String movieRank) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.movieImgUrl=movieImgUrl;
        this.movieRank=movieRank;
    }

}
