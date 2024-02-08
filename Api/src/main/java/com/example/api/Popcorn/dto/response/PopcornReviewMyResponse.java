package com.example.api.Popcorn.dto.response;

import com.example.domains.popcorn.entity.Popcorn;
import com.example.domains.popcornUser.entity.PopcornUser;
import com.example.domains.popcornUser.entity.enums.PopcornNegative;
import com.example.domains.popcornUser.entity.enums.PopcornPositive;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PopcornReviewMyResponse {
    @Schema(defaultValue = "1", description = "유저 id")
    private Long userId;
    @Schema(defaultValue = "닉네임", description = "유저 닉네임")
    private String nickName;
    @Schema(defaultValue = "5", description = "프로필 이미지")
    private int profileImgNum;
    @Schema(defaultValue = "2",description = "팝콘작 id")
    private Popcorn popcorn;
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
    @Schema(description = "Positive attributes")
    private PopcornPositive popcornPositive;
    @Schema(description = "Negative attributes")
    private PopcornNegative popcornNegative;

    @Builder
    public PopcornReviewMyResponse(Long userId,String nickName,int profileImgNum,Popcorn popcorn,boolean hasWatched, boolean beforeScreening, boolean afterScreening, String review, boolean hasAgreed, PopcornPositive popcornPositive, PopcornNegative popcornNegative) {
        this.userId=userId;
        this.nickName=nickName;
        this.profileImgNum=profileImgNum;
        this.popcorn=popcorn;
        this.hasWatched=hasWatched;
        this.beforeScreening = beforeScreening;
        this.afterScreening = afterScreening;
        this.review = review;
        this.hasAgreed =hasAgreed;
        this.popcornPositive = popcornPositive;
        this.popcornNegative = popcornNegative;
    }

    public static PopcornReviewMyResponse from(PopcornUser popcornUser) {
        return PopcornReviewMyResponse.builder()
                .userId(popcornUser.getUser().getId())
                .nickName(popcornUser.getUser().getNickname())
                .profileImgNum(popcornUser.getUser().getProfileImgNum())
                .popcorn(popcornUser.getPopcorn())
                .hasWatched(popcornUser.isHasWatched())
                .beforeScreening(popcornUser.isBeforeScreening())
                .afterScreening(popcornUser.isAfterScreening())
                .review(popcornUser.getReview())
                .popcornPositive(popcornUser.getPopcornPositive())
                .popcornNegative(popcornUser.getPopcornNegative())
                .build();
    }
}
