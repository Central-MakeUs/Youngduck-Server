package com.example.domains.popcornUser.entity;

import com.example.domains.common.model.BaseTimeEntity;
import com.example.domains.popcorn.entity.Popcorn;
import com.example.domains.popcornUser.entity.enums.PopcornNegative;
import com.example.domains.popcornUser.entity.enums.PopcornPositive;
import com.example.domains.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PopcornUser extends BaseTimeEntity {
    //Popcorn이랑 User사이에 있는 엔티티, 리뷰 작성도 여기서 이루어짐
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "popcorn_id")
    private Popcorn popcorn;


    private String review;

    private int likes;
    private boolean hasWatched;
    private boolean beforeScreening;
    private boolean afterScreening;
    private boolean hasAgreed;
    private int complaintCount;

    private boolean isBlind;

    @Embedded
    private PopcornPositive popcornPositive;

    @Embedded
    private PopcornNegative popcornNegative;

    @Builder
    private PopcornUser (boolean hasWatched,boolean beforeScreening,boolean afterScreening, String review, boolean hasAgreed,PopcornPositive popcornPositive,PopcornNegative popcornNegative,User user, Popcorn popcorn) {
        this.hasWatched=hasWatched;
        this.beforeScreening = beforeScreening;
        this.afterScreening = afterScreening;
        this.review = review;
        this.hasAgreed =hasAgreed;
        this.popcornPositive = popcornPositive;
        this.popcornNegative = popcornNegative;
        this.user = user;
        this.popcorn = popcorn;
    }

    public static PopcornUser of(boolean hasWatched,boolean beforeScreening,boolean afterScreening, String review, boolean hasAgreed,PopcornPositive popcornPositive,PopcornNegative popcornNegative,User user, Popcorn popcorn){
        return PopcornUser.builder()
                .hasWatched(hasWatched)
                .beforeScreening(beforeScreening)
                .afterScreening(afterScreening)
                .review(review)
                .hasWatched(hasAgreed)
                .popcornPositive(popcornPositive)
                .popcornNegative(popcornNegative)
                .user(user)
                .popcorn(popcorn)
                .build();
    }
}
