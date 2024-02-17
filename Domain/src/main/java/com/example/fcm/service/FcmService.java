package com.example.fcm.service;

import com.example.domains.user.entity.User;
import com.example.domains.user.exception.exceptions.UserNotFoundException;
import com.example.domains.user.repository.UserRepository;
import com.example.fcm.entity.FCMToken;
import com.example.fcm.repository.FcmRepository;
import com.example.fcm.request.FcmRegistrationRequest;
import com.example.fcm.request.NotificationRequest;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FcmService {
    private final FirebaseMessaging firebaseMessaging;
    private final UserRepository userRepository;
    private final FcmRepository fcmRepository;
    @Transactional
    public void registerFCMToken(Long userId, FcmRegistrationRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        // 이미 등록된 FCMToken이 있는지 확인
        Optional<FCMToken> existingToken = fcmRepository.findByUserId(userId);

        if (existingToken.isPresent()) {
            // 이미 등록된 FCMToken이 있는 경우 값을 업데이트
            existingToken.get().updateToken(request.getFcmToken());
        } else {
            // 등록된 FCMToken이 없는 경우 새로 생성하여 저장
            FCMToken newToken = FCMToken.createFCMToken(user, request.getFcmToken());
            fcmRepository.save(newToken);
        }
    }


    public void sendMessageByToken(NotificationRequest request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(IllegalArgumentException::new);
        FCMToken fcm = fcmRepository.findByUserId(user.getId()).get();
        String fcmToken = fcm.getFcmToken();
        if (!fcmToken.isEmpty()) {
            Message message = getMessage(request, fcmToken);

            try {
                firebaseMessaging.send(message);
                log.info("푸시 알림 전송 완료 userId = {}", user.getId());
            } catch (FirebaseMessagingException e) {
                log.info("푸시 알림 전송 실패 userId = {}", user.getId());
                throw new RuntimeException(e);
            }
        }
    }

    private static Message getMessage(NotificationRequest request, String fcmToken) {
        Notification notification = Notification.builder()
                .setTitle(request.getTitle())
                .setBody(request.getBody())
                .build();

        Message message = Message.builder()
                .setToken(fcmToken)
                .setNotification(notification)
                .build();
        return message;
    }
}