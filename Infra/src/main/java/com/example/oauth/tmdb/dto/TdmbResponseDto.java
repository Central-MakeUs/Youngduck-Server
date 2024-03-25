package com.example.oauth.tmdb.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TdmbResponseDto {
    private String movieId;
    private String movieSeq;
    private String title;
    private String directorNm;
    private String plotText;
    //private String posterUrls;
    public String  getMovieId() {
        return movieId;
    }
    public String  getMovieSeq() {
        return movieSeq;
    }
    public String  getTitle() {
        return title;
    }
    public String  getDirectorNm() {
        return directorNm;
    }
    public String  getPlotText() {
        return plotText;
    }

}
