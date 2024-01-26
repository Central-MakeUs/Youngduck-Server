package com.example.api.screening.dto.response;

import com.example.domains.screeningReview.entity.dto.ScreeningReviewResponseDto;
import com.example.domains.user.entity.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
public class ScreeningReviewUserResponse implements Serializable {
    @Schema(defaultValue = "1", description = "리뷰 아이디")
    private Long reviewId;

    @Schema(defaultValue = "true", description = "작품")
    private boolean afterScreening;
    @Schema(defaultValue = "true", description = "작품")
    private LocalDateTime createdAt;

    @Schema(defaultValue = "1", description = "작품")
    private Long screeningId;
    @Schema(defaultValue = "tsdfhjsdf", description = "작품")
    private String review;
    @Schema(defaultValue = "1", description = "작품")
    private Long userId;

    @Schema(defaultValue = "팝콩이", description = "작품")
    private String nickname;

    @Schema(defaultValue = "5", description = "작품")
    private int profileImageNumber;


    @Builder
    public ScreeningReviewUserResponse(Long reviewId,boolean afterScreening, String review, LocalDateTime createdAt, Long screeningId,Long userId, String nickname,int profileImageNumber) {
        this.reviewId = reviewId;
        this.afterScreening = afterScreening;
        this.review = review;
        this.createdAt = createdAt;
        this.screeningId = screeningId;
        this.userId = userId;
        this.nickname = nickname;
        this.profileImageNumber = profileImageNumber;
    }
    public static ScreeningReviewUserResponse from(ScreeningReviewResponseDto screeningReview,User user) {
        return  ScreeningReviewUserResponse.builder()
                .reviewId(screeningReview.getReviewId())
                .afterScreening(screeningReview.isAfterScreening())
                .review(screeningReview.getReview())
                .createdAt(screeningReview.getCreatedAt())
                .screeningId(screeningReview.getScreeningId())
                .userId(user.getId())
                .nickname(user.getNickname())
                .profileImageNumber(5)
                .build();
    }
}
