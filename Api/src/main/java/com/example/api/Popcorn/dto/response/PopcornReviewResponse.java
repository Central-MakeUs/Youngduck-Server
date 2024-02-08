package com.example.api.Popcorn.dto.response;

import com.example.domains.popcornUser.entity.PopcornUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PopcornReviewResponse {
    @Schema(defaultValue = "1", description = "PopcornUser id")
    private Long popcornUserId;
    @Schema(defaultValue = "1", description = "유저 id")
    private Long userId;
    @Schema(defaultValue = "닉네임", description = "유저 닉네임")
    private String nickName;
    @Schema(defaultValue = "5", description = "프로필 이미지")
    private int profileImgNum;
    @Schema(defaultValue = "2",description = "팝콘작 id")
    private Long popcornId;
    @Schema(defaultValue = "true", description = "관람 여부")
    private boolean hasWatched;
    @Schema(defaultValue = "true", description = "관람 전 만족도")
    private boolean beforeScreening;
    @Schema(defaultValue = "true", description = "관람 후 만족도")
    private boolean afterScreening;
    @Schema(defaultValue = "너무 좋았습니다", description = "텍스트 리뷰")
    private String review;
    @Schema(defaultValue = "true", description = "정책 약관")
    private boolean hasAgreed;
    @Schema(defaultValue = "2024-01-31", description = "리뷰 등록 일자")
    private LocalDateTime createdAt;

    @Builder
    public PopcornReviewResponse(Long popcornUserId,Long userId, String nickName, int profileImgNum, Long popcornId, boolean hasWatched, boolean beforeScreening, boolean afterScreening, String review, boolean hasAgreed, LocalDateTime createdAt) {
        this.popcornUserId = popcornUserId;
        this.userId=userId;
        this.nickName=nickName;
        this.profileImgNum=profileImgNum;
        this.popcornId=popcornId;
        this.hasWatched=hasWatched;
        this.beforeScreening = beforeScreening;
        this.afterScreening = afterScreening;
        this.review = review;
        this.hasAgreed =hasAgreed;
        this.createdAt = createdAt;
    }
    public static PopcornReviewResponse from(PopcornUser popcornUser) {
        return PopcornReviewResponse.builder()
                .popcornUserId(popcornUser.getId())
                .userId(popcornUser.getUser().getId())
                .nickName(popcornUser.getUser().getNickname())
                .profileImgNum(popcornUser.getUser().getProfileImgNum())
                .popcornId(popcornUser.getPopcorn().getId())
                .hasWatched(popcornUser.isHasWatched())
                .beforeScreening(popcornUser.isBeforeScreening())
                .afterScreening(popcornUser.isAfterScreening())
                .review(popcornUser.getReview())
                .createdAt(popcornUser.getCreatedAt())
                .build();
    }
}
