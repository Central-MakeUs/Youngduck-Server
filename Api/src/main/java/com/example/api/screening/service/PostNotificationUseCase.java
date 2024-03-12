package com.example.api.screening.service;

import com.example.adaptor.UseCase;
import com.example.domains.screeningReview.adaptor.ReviewAdaptor;
import com.example.domains.userscreening.adaptor.UserScreeningAdaptor;
import com.example.kafka.producer.service.ProducerService;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class PostNotificationUseCase {
    private final ProducerService producerService;
    private final UserScreeningAdaptor userScreeningAdaptor;


    private final ReviewAdaptor reviewAdaptor;
//    public NotificationResponse execute(ReviewNotificationRequest request) {
//        Long userId = SecurityUtil.getCurrentUserId();
//        String message = request.get
//        producerService.commentNotificationCreate(,message);
//    }
}
