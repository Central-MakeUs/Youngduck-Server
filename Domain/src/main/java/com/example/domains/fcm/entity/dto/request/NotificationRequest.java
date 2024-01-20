package com.example.domains.fcm.entity.dto.request;

import com.example.domains.screening.adaptor.ScreeningAdaptor;
import com.example.domains.screening.entity.Screening;
import com.example.domains.userscreening.entity.UserScreening;
import lombok.Getter;

@Getter
public class NotificationRequest {
    private Long userId;
    private String title;
    private String body;

    public NotificationRequest(Screening screening, Long user, String title) {
        this.userId = user;
        this.title = title;
        this.body = screening.getTitle() + " 상영회 하루 전 입니다.";
    }
}