package com.example.domains.screeningReview.entity.dto;

import com.example.domains.screeningReview.entity.ScreeningReview;
import com.example.domains.user.entity.User;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class ScreeningReviewResponseDto {
    private Long reviewId;
    private boolean afterScreening;
    private LocalDateTime createdAt;
    private Long screeningId;
    private String review;
    private Long userId;

    private String nickname;
    private int profileImgNumber;
   @QueryProjection
    public ScreeningReviewResponseDto(Long reviewId,boolean afterScreening, LocalDateTime createdAt, Long screeningId,String review,Long userId,String nickname,int profileImgNumber) {
       this.reviewId = reviewId;
       this.afterScreening = afterScreening;
        this.createdAt = createdAt;
        this.screeningId = screeningId;
        this.review = review;
        this.userId =userId;
        this.nickname =nickname;
        this.profileImgNumber =profileImgNumber;
    }
//    public static ScreeningReviewResponseDto from(ScreeningReviewResponseDto screeningReview) {
//        return  ScreeningReviewResponseDto.builder()
//                .afterScreening(screeningReview.isAfterScreening())
//                .review(screeningReview.getReview())
//                .createdAt(screeningReview.getCreatedAt())
//                .screeningId(screeningReview.getScreeningId())
//                .build();
//    }
}
