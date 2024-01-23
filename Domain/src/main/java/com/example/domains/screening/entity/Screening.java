package com.example.domains.screening.entity;

import com.example.domains.common.model.BaseTimeEntity;
import com.example.domains.screening.enums.Category;
import com.example.domains.screening.enums.HostInfo;
import com.example.domains.screening.enums.NegativeCount;
import com.example.domains.screening.enums.PositiveCount;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

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

    @Embedded
    private PositiveCount positiveCount = new PositiveCount();

    @Embedded
    private NegativeCount negativeCount = new NegativeCount();

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
    private int screeningRate;


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


    public void updatePrivacy(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public void update(Screening request) {
        // Update the screening with the new information from the request
        this.title = request.getTitle();
        this.posterImgUrl = request.getPosterImgUrl();
        this.hostInfo = HostInfo.of(request.hostInfo.getHostName(), request.hostInfo.getHostPhoneNumber(), request.hostInfo.getHostEmail());
        this.location = request.getLocation();
        this.participationUrl = request.getParticipationUrl();
        this.information = request.getInformation();
        this.screeningStartDate = request.getScreeningStartDate();
        this.screeningEndDate = request.getScreeningEndDate();
        this.screeningStartTime = request.getScreeningStartTime();
        this.hasAgreed = request.isHasAgreed();
        this.category = request.getCategory();
    }

}
