package com.example.api.screening.dto.response;

import com.example.domains.screening.entity.Screening;
import com.example.domains.screening.enums.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScreeningInfoResponse {
    @Schema(defaultValue = "1", description = "스크리닝 id")
    private Long screeningId;

    @Schema(defaultValue = "https://jgjfhdjghsdkjhgkjd", description = "상영회 대표 이미지")
    private String posterImgUrl;

    @Schema(defaultValue = "홍익대학교 졸업전시회", description = "상영회 제목")
    private String screeningTitle;

    @Schema(defaultValue = "이한비", description = "주최자명")
    private String hostName;

    @Schema(defaultValue = "졸업상영", description = "카테고리(\"졸업상영\"/\"과제상영\"/\"정기상영\"/\"특별상영\"/\"기타\")")
    private Category category;

    @Schema(defaultValue = "2023-02-13", description = "시작 날짜")
    private LocalDateTime screeningStartDate;

    @Schema(defaultValue = "2023-02-14", description = "종료 날짜")
    private LocalDateTime screeningEndDate;

    @Schema(defaultValue = "13:00", description = "시작 시간")
    private LocalDateTime screeningStartTime;

    @Schema(defaultValue = "홍익대학교", description = "주최 장소")
    private String location;

    @Schema(defaultValue = "졸업 작품보러오세요", description = "상영회 정보")
    private String information;

    @Schema(defaultValue = "https://sdhgfhsdjkfsjjgsh.com", description = "신청 폼 링크")
    private String formUrl;

    @Schema(defaultValue = "010-0000-0000", description = "주최자 전화번호")
    private String hostPhoneNumber;

    @Schema(defaultValue = "email1111@email.com", description = "주최자 이메일")
    private String hostEmail;
    @Schema(defaultValue = "true", description = "정책 동의 여부")
    private boolean hasAgreed;

    @Schema(defaultValue = "false", description = "정책 동의 여부")
    private boolean isPrivate;

    @Schema(defaultValue = "false", description = "찜하기 여부")
    private boolean isBookmarked;

    @Schema(defaultValue = "false", description = "리뷰 여부")
    private boolean isReviewed;


    @Builder
    public ScreeningInfoResponse(Long screeningId,
                             String screeningTitle, String posterImgUrl, String hostName, String hostEmail, String hostPhoneNumber , String location, String formUrl,
                             String information, boolean hasAgreed, Category category, LocalDateTime screeningStartDate, LocalDateTime screeningEndDate, LocalDateTime screeningStartTime,
                             boolean isPrivate, boolean isBookmarked, boolean isReviewed
    ) {
        this.screeningId = screeningId;
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
        this.isBookmarked = isBookmarked;
        this.isReviewed = isReviewed;
    }

    public static ScreeningInfoResponse from(Screening screening,boolean isBookmarked, boolean isReviewed) {
        return ScreeningInfoResponse .builder()
                .screeningId(screening.getId())
                .screeningTitle(screening.getTitle())
                .posterImgUrl(screening.getPosterImgUrl())
                .hostName(screening.getHostInfo().getHostName())
                .location(screening.getLocation())
                .formUrl(screening.getParticipationUrl())
                .information(screening.getInformation())
                .hasAgreed(screening.isHasAgreed())
                .screeningStartDate(screening.getScreeningStartDate())
                .screeningEndDate(screening.getScreeningEndDate())
                .screeningStartTime(screening.getScreeningStartTime())
                .category(screening.getCategory())
                .hostEmail(screening.getHostInfo().getHostEmail())
                .hostPhoneNumber(screening.getHostInfo().getHostPhoneNumber())
                .isPrivate(screening.isPrivate())
                .isBookmarked(isBookmarked)
                .isReviewed(isReviewed)
                .build();
    }

}
