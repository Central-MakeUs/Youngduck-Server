package com.example.api.screening.dto.response;

import com.example.domains.screening.entity.dto.ScreeningCountDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GetCountResponse {
    @Schema(defaultValue = "홍익대학교 졸업전시회", description = "상영회 제목")
    private int movieReviewCountNeg;
    @Schema(defaultValue = "홍익대학교 졸업전시회", description = "상영회 제목")
    private int movieReviewCountPos;
    @Schema(defaultValue = "홍익대학교 졸업전시회", description = "상영회 제목")
    private int locationCountNeg;
    @Schema(defaultValue = "홍익대학교 졸업전시회", description = "상영회 제목")
    private int locationCountPos;
    @Schema(defaultValue = "홍익대학교 졸업전시회", description = "상영회 제목")
    private int serviceCountNeg;
    @Schema(defaultValue = "홍익대학교 졸업전시회", description = "상영회 제목")
    private int serviceCountPos;
    @Schema(defaultValue = "홍익대학교 졸업전시회", description = "상영회 제목")
    private Long screeningRate;

    @Builder
    public GetCountResponse(int movieReviewCountNeg,int movieReviewCountPos,int locationCountNeg,int locationCountPos,int serviceCountNeg,int serviceCountPos,Long screeningRate){
        this.movieReviewCountNeg = movieReviewCountNeg;
        this.movieReviewCountPos = movieReviewCountPos;
        this.locationCountNeg = locationCountNeg;
        this.locationCountPos = locationCountPos;
        this.serviceCountNeg = serviceCountNeg;
        this.serviceCountPos = serviceCountPos;
        this.screeningRate = screeningRate;
    }
    public static GetCountResponse from(ScreeningCountDto screeningCountDto) {
        return GetCountResponse.builder()
                .locationCountNeg(screeningCountDto.getLocationCountNeg())
                .locationCountPos(screeningCountDto.getLocationCountPos())
                .movieReviewCountNeg(screeningCountDto.getMovieReviewCountNeg())
                .movieReviewCountPos(screeningCountDto.getMovieReviewCountPos())
                .serviceCountNeg(screeningCountDto.getServiceCountNeg())
                .serviceCountPos(screeningCountDto.getServiceCountPos())
                .screeningRate(screeningCountDto.getScreeningRate())
                .build();
    }
}
