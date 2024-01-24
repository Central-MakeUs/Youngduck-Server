package com.example.domains.screening.entity.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ScreeningCountDto {
    private int movieReviewCountNeg;
    private int movieReviewCountPos;
    private int locationCountNeg;
    private int locationCountPos;
    private int serviceCountNeg;
    private int serviceCountPos;
    private Long screeningRate;

    @Builder
    public ScreeningCountDto(int movieReviewCountNeg,int movieReviewCountPos,int locationCountNeg,int locationCountPos,int serviceCountNeg,int serviceCountPos,Long screeningRate){
        this.movieReviewCountNeg = movieReviewCountNeg;
        this.movieReviewCountPos = movieReviewCountPos;
        this.locationCountNeg = locationCountNeg;
        this.locationCountPos = locationCountPos;
        this.serviceCountNeg = serviceCountNeg;
        this.serviceCountPos = serviceCountPos;
        this.screeningRate = screeningRate;
    }
}
