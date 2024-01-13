package com.example.domains.screeningReview.entity;

import com.example.domains.common.model.BaseTimeEntity;
import com.example.domains.userscreening.entity.UserScreening;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScreeningReview extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String review;

    private int likes;

    private boolean beforeScreening;
    private boolean afterScreening;
    private boolean movieReview;
    private boolean locationReview;
    private boolean serviceReview;

    @OneToOne
    @JoinColumn(name = "user_screening_id")
    private UserScreening userScreening;

}
