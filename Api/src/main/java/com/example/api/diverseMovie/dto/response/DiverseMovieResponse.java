package com.example.api.diverseMovie.dto.response;

import com.example.api.screening.dto.response.BookMarkResponse;
import com.example.domains.diverseMovie.entity.dto.DiverseMovieResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class DiverseMovieResponse {
    @Schema(defaultValue = "1", description = "독립영화")
    private Long movieId;
    @Schema(defaultValue = "괴물", description = "영화제목")
    private String movieTitle;

    @Schema(defaultValue = "asfdhihfajsfodhasf.jpg", description = "영화포스터 이미지")
    private String imageUrl;

    @Schema(defaultValue = "1", description = "영화 순위")
    private String rank;

    @Builder
    public DiverseMovieResponse(Long movieId,String movieTitle,String imageUrl, String rank){
        this.movieId=movieId;
        this.movieTitle=movieTitle;
        this.imageUrl=imageUrl;
        this.rank=rank;
    }
    public static DiverseMovieResponse from(DiverseMovieResponseDto diverseMovieResponseDto){
        return DiverseMovieResponse.builder()
                .movieId(diverseMovieResponseDto.getMovieId())
                .movieTitle(diverseMovieResponseDto.getMovieTitle())
                .imageUrl(diverseMovieResponseDto.getMovieImgUrl())
                .rank(diverseMovieResponseDto.getMovieRank())
                .build();
    }
}
