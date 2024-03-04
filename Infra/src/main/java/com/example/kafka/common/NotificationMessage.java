package com.example.kafka.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class NotificationMessage {
    private String userId;
    private String message;
}