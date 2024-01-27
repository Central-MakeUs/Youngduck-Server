package com.example.api.Popcorn.dto.response;

import com.example.api.diverseMovie.dto.response.DiverseMovieResponse;
import com.example.domains.diverseMovie.entity.dto.DiverseMovieResponseDto;
import com.example.domains.popcorn.entity.Popcorn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PopcornResponse {
    @Schema(defaultValue = "1", description = "합콘아이디")
    private long popcornId;
    @Schema(defaultValue = "괴물", description = "영화제목")
    private String movieTitle;

    @Schema(defaultValue = "고레에다 히로카즈", description = "영화김독")
    private String directorName;
    @Schema(defaultValue = "gfhffgvgvghdfhdchj.jpg", description = "영화포스터 이미지")
    private String imageUrl;


    @Builder
    public PopcornResponse(Long popcornId,String movieTitle,String directorName,String imageUrl){
        this.popcornId = popcornId;
        this.movieTitle=movieTitle;
        this.directorName=directorName;
        this.imageUrl = imageUrl;
    }
    public static PopcornResponse from(Popcorn popcorn){
        return PopcornResponse.builder()
                .popcornId(popcorn.getId())
                .movieTitle(popcorn.getMovieTitle())
                .directorName(popcorn.getDirectorName())
                .imageUrl(popcorn.getImageUrl())
                .build();

    }
}
