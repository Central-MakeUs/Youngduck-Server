package com.example.api.fcm.controller;

import com.example.api.config.security.SecurityUtil;
import com.example.fcm.request.FcmRegistrationRequest;
import com.example.fcm.service.FcmService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static io.netty.handler.codec.http.HttpResponseStatus.CREATED;

@Slf4j
@RestController
@RequestMapping("/fcm")
@RequiredArgsConstructor
public class FcmController {

    private final FcmService fcmService;

    @PostMapping("/{userId}")
    public ResponseEntity<Void> fcmTokenRegistration(
            @PathVariable("userId") Long userId,
            @RequestBody FcmRegistrationRequest request) {
//        Long userId = SecurityUtil.getCurrentUserId();
        fcmService.registerFCMToken(userId, request);
        return ResponseEntity
                .status(CREATED.code())
                .build();
    }

    @GetMapping
    public void fcmToken() throws FirebaseAuthException {
        String uid = "some-uid";

        String customToken = FirebaseAuth.getInstance().createCustomToken(uid);
        System.out.println(customToken);
    }

}
