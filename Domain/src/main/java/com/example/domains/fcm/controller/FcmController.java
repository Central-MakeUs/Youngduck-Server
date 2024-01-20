package com.example.domains.fcm.controller;

import com.example.domains.fcm.entity.dto.request.FcmRegisterRequest;
import com.example.domains.fcm.service.FcmService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RestController
@RequestMapping("/fcm")
@RequiredArgsConstructor
public class FcmController {

    private final FcmService fcmService;

    @PostMapping
    public ResponseEntity<Void> fcmTokenRegistration(
            @RequestHeader(value = "user-id") Long userId,
            @Valid @RequestBody FcmRegisterRequest request) {
        fcmService.registerFCMToken(userId, request);
        return ResponseEntity
                .status(CREATED)
                .build();
    }
}