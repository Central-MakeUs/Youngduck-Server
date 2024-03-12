package com.example.kafka.common;

import lombok.*;
import org.springframework.kafka.annotation.KafkaListener;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access=AccessLevel.PROTECTED)
public class NotificationMessage {

    private Long userId;
    private String message;

}