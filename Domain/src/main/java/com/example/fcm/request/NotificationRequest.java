package com.example.fcm.request;

import com.example.domains.screening.entity.Screening;
import lombok.Getter;

@Getter
public class NotificationRequest {
    private Long userId;
    private String title;
    private String body;

    public NotificationRequest(Screening screening, Long user, String title) {
        this.userId= user;
        this.title = title;
        this.body = screening.getInformation();
    }
}
