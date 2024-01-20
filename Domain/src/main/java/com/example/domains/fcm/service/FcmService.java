package com.example.domains.fcm.service;

import com.example.domains.fcm.adaptor.FcmAdaptor;
import com.example.domains.fcm.entity.FcmToken;
import com.example.domains.fcm.entity.dto.request.FcmRegisterRequest;
import com.example.domains.fcm.entity.dto.request.NotificationRequest;
import com.example.domains.fcm.repository.FcmRepository;
import com.example.domains.user.adaptor.UserAdaptor;
import com.example.domains.user.entity.User;
import com.example.domains.user.repository.UserRepository;
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
    private final UserAdaptor userAdaptor;
    private final FcmAdaptor fcmAdaptor;

    public void registerFCMToken(Long userId, FcmRegisterRequest request) {
        User user = userAdaptor.findById(userId);
        FcmToken fcmToken = FcmToken.createFCMToken(user, request.getFcmToken());


        fcmAdaptor.save(fcmToken);
    }

    public void sendMessageByToken(NotificationRequest request) {
        User user = userAdaptor.findById(request.getUserId());

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