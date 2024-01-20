package com.example.domains.fcm.entity;

import com.example.domains.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FcmToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id", nullable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "fcm_token")
    private String fcmToken;

    @Builder(access = AccessLevel.PRIVATE)
    private FcmToken(User user, String fcmToken) {
        this.user = user;
        this.fcmToken = fcmToken;
    }

    public static FcmToken createFCMToken(User user, String fcmToken) {
        return FcmToken.builder()
                .user(user)
                .fcmToken(fcmToken)
                .build();
    }
}