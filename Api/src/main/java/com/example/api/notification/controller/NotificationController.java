package com.example.api.notification.controller;

import com.example.api.config.security.SecurityUtil;
import com.example.domains.kafka.emitter.service.EmitterService;
import com.example.domains.kafka.notifications.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@RestController
@Slf4j
@RequiredArgsConstructor
public class NotificationController {
    private final EmitterService emitterService;
    private final NotificationService notificationService;

    public static final Long DEFAULT_TIMEOUT = 3600L * 1000;

    @GetMapping(value = "/api/sse-connection", produces = "text/event-stream")
    public SseEmitter stream(@RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) throws IOException {
        Long userId = SecurityUtil.getCurrentUserId();
        return emitterService.addEmitter(String.valueOf(userId), lastEventId);
    }

}
