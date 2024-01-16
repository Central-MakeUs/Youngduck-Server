package com.example.domains.screening.entity.dto;

import com.example.domains.screening.enums.Category;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Getter
@NoArgsConstructor
public class ScreeningResponseDto {

    private String posterImgUrl;


    private String screeningTitle;


    private String hostName;


    private Category category;

    private LocalDateTime screeningStartDate;


    private LocalDateTime screeningEndDate;

    private LocalDateTime screeningStartTime;

    private String location;

    private String information;

    private String formUrl;


    private String hostPhoneNumber;

    private String hostEmail;

    private boolean hasAgreed;

    private boolean isPrivate;


    @QueryProjection
    public ScreeningResponseDto(
            String screeningTitle, String posterImgUrl, String hostName, String hostEmail, String hostPhoneNumber , String location, String formUrl,
            String information, boolean hasAgreed, Category category, LocalDateTime screeningStartDate, LocalDateTime screeningEndDate, LocalDateTime screeningStartTime,
            boolean isPrivate
    ) {
        this.screeningTitle = screeningTitle;
        this.posterImgUrl = posterImgUrl;
        this.hostName = hostName;
        this.location = location;
        this.formUrl = formUrl;
        this.information = information;
        this.hasAgreed = hasAgreed;
        this.screeningStartDate = screeningStartDate;
        this.screeningEndDate = screeningEndDate;
        this.screeningStartTime = screeningStartTime;
        this.category = category;
        this.hostEmail = hostEmail;
        this.hostPhoneNumber = hostPhoneNumber;
        this.isPrivate = isPrivate;
    }
}
