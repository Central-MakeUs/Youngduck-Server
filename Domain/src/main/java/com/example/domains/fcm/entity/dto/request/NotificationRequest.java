package com.example.domains.fcm.entity.dto.request;

import com.example.domains.userscreening.entity.UserScreening;
import lombok.Getter;

@Getter
public class NotificationRequest {
    private Long userId;
    private String title;
    private String body;

    public NotificationRequest(UserScreening userScreening, String title) {
        this.userId = userScreening.getUser().getId();
        this.title = title;
        this.body = userScreening.getScreening().getScreeningStartDate() + " 상영회 하루 전 입니다.";
    }
}