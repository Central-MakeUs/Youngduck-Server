package com.example.domains.screeningReview.entity.dto;

import com.example.domains.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
public class ScreeningReviewUserResponseDto {

    private boolean afterScreening;
    private LocalDateTime createdAt;
    private Long screeningId;
    private String review;
    private User user;
    @Builder
    public ScreeningReviewUserResponseDto( boolean afterScreening, String review, LocalDateTime createdAt, Long screeningId,User user) {
        this.afterScreening = afterScreening;
        this.review = review;
        this.createdAt = createdAt;
        this.screeningId = screeningId;
        this.user = user;
    }
    public static ScreeningReviewUserResponseDto  from(ScreeningReviewResponseDto screeningReview, User user) {
        return  ScreeningReviewUserResponseDto.builder()
                .afterScreening(screeningReview.isAfterScreening())
                .review(screeningReview.getReview())
                .createdAt(screeningReview.getCreatedAt())
                .screeningId(screeningReview.getScreeningId())
                .user(user)
                .build();
    }
}

//Response로 만들기
