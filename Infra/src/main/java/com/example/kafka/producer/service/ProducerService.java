package com.example.kafka.producer.service;

import com.example.kafka.common.NotificationMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProducerService {
    private final KafkaTemplate<String, NotificationMessage> kafkaTemplate;

    public void commentNotificationCreate(String userId, String message) {
        long newId = Long.parseLong(userId);
        NotificationMessage notificationMessage = new NotificationMessage(newId , message);
        log.info("리뷰 답글 알림 전송. userId : {}, message : {}",userId, message);
        kafkaTemplate.send("comment-notifications", notificationMessage);
    }
}