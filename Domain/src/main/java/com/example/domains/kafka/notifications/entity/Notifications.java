package com.example.domains.kafka.notifications.entity;

import com.example.domains.common.model.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@ToString
@NoArgsConstructor
@Entity
public class Notifications extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationsId;
    private Long userId;
    private Long reviewId;
    private String notificationsMessage;
    private Timestamp isRead;

    @Builder
    public Notifications(Long userId,Long reviewId, String notificationsMessage) {
        this.userId = userId;
        this.reviewId = reviewId;
        this.notificationsMessage = notificationsMessage;
    }
}