package com.example.domains.screening.entity;

import com.example.domains.common.model.BaseTimeEntity;
import com.example.domains.screening.enums.Category;
import com.example.domains.screening.enums.HostInfo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Screening extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String posterImgUrl;
    @Embedded
    private HostInfo hostInfo;

    private Date month;
    private LocalDateTime screeningStartDate;
    private LocalDateTime screeningEndDate;
    private LocalDateTime screeningStartTime;

    private String location;
    private String participationUrl;

    private String information;
    private boolean hasAgreed;
    private boolean isPrivate;
    private Category category;

    @Builder
    private Screening(String title, String posterImgUrl, HostInfo hostInfo, String location, String participationUrl,
                      String information, boolean hasAgreed, Category category, LocalDateTime screeningStartDate, LocalDateTime screeningEndDate, LocalDateTime screeningStartTime) {
        this.title = title;
        this.posterImgUrl = posterImgUrl;
        this.hostInfo = hostInfo;
        this.location = location;
        this.participationUrl = participationUrl;
        this.information = information;
        this.screeningStartDate = screeningStartDate;
        this.screeningEndDate = screeningEndDate;
        this.screeningStartTime = screeningStartTime;
        this.hasAgreed = hasAgreed;
        this.category = category;
    }

    public static Screening of(
            String title, String posterImgUrl, HostInfo hostInfo, String location, String participationUrl,
            String information, LocalDateTime screeningStartDate, LocalDateTime screeningEndDate, LocalDateTime screeningStartTime ,boolean hasAgreed, Category category
    ){
        return Screening.builder()
                .title(title)
                .posterImgUrl(posterImgUrl)
                .hostInfo(hostInfo)
                .location(location)
                .participationUrl(participationUrl)
                .information(information)
                .screeningStartDate(screeningStartDate)
                .screeningEndDate(screeningEndDate)
                .screeningStartTime(screeningStartTime)
                .hasAgreed(hasAgreed)
                .category(category)
                .build();
    }
}