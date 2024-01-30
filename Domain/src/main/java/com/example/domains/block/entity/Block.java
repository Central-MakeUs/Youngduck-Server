package com.example.domains.block.entity;

import com.example.domains.common.model.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Block extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long blockedUserId;
    private Long screeningReviewId;
    private Long popcornReviewId;

    @Builder
    public Block(Long userId, Long blockedUserId,Long screeningReviewId,Long popcornReviewId){
        this.userId=userId;
        this.blockedUserId=blockedUserId;
        this.screeningReviewId = screeningReviewId;
        this.popcornReviewId = popcornReviewId;
    }
    public static Block of(Long userId, Long blockedUserId,Long screeningReviewId,Long popcornReviewId) {
        return Block.builder()
                .userId(userId)
                .blockedUserId(blockedUserId)
                .screeningReviewId(screeningReviewId)
                .popcornReviewId(popcornReviewId)
                .build();
    }
}
