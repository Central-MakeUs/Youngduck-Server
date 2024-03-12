package com.example.domains.kafka.notifications.service;

import com.example.domains.kafka.notifications.entity.Notifications;
import com.example.domains.kafka.notifications.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class NotificationService {
    private final NotificationRepository notificationsRepository;

    public void insertNotifications(Notifications notifications) {
        Notifications newNotifications = Notifications.builder()
                .userId(notifications.getUserId())
                .reviewId(notifications.getReviewId())
                .notificationsMessage(notifications.getNotificationsMessage())
                .build();
        notificationsRepository.save(newNotifications);
    }

//    public void deleteOldestNotificationsByUserId(Long userIdNo, int delCount) {
//        notificationsRepository.deleteOldestNotificationsByUserId(userIdNo, delCount);
//    }
//
//    public int countNotificationsByUserId(Long userIdNo) {
//        return notificationsRepository.countNotificationsByUserId(userIdNo);
//    }

}