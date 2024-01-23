package com.example.domains.popcornUser.entity;

import com.example.domains.common.model.BaseTimeEntity;
import com.example.domains.popcorn.entity.Popcorn;
import com.example.domains.screening.entity.Screening;
import com.example.domains.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PopcornUser extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "popcorn_id")
    private Popcorn popcorn;
}
