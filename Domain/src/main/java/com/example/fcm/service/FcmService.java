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

@Slf4j
@Service
@RequiredArgsConstructor
public class FcmService {
    private final FirebaseMessaging firebaseMessaging;
    private final UserRepository userRepository;
    private final FcmRepository fcmRepository;
    public void registerFCMToken(Long userId, FcmRegistrationRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);
        FCMToken fcmToken = FCMToken.createFCMToken(user, request.getFcmToken());

        fcmRepository.save(fcmToken);
    }


    public void sendMessageByToken(NotificationRequest request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(IllegalArgumentException::new);

        String fcmToken = user.getFcmToken().getFcmToken();
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