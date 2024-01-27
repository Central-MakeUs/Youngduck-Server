package com.example.domains.common;

import com.example.domains.screening.adaptor.ScreeningAdaptor;
import com.example.domains.screening.entity.Screening;
import com.example.domains.userscreening.adaptor.UserScreeningAdaptor;
import com.example.domains.userscreening.entity.UserScreening;
import com.example.fcm.request.NotificationRequest;
import com.example.fcm.service.FcmService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final FcmService fcmService;

    private final UserScreeningAdaptor userScreeningAdaptor;
    private final ScreeningAdaptor screeningAdaptor;

    private static final String NOTIFICATION_TITLE = "상영회 하루 전 알림";
//    @Scheduled(cron = "0 0/1 * * * *")
//    private void notifyReservation() {
//        LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
//        LocalDateTime reservationTime = now.plusDays(1);
//
//        //userScreening에서 isBookMarked인 것들 중에서 user id, screening id가져와서 List<User> List<Screening>
//        //screening에서 startDate가져와서 startDate가 내일이면 알람을 보낼 수 있게 짜봐 fcm이랑 스프링 쓰고 있어
//
//        List<UserScreening> bookmarkedUserScreenings =  userScreeningAdaptor.findByBookMarked();
//
//        for (UserScreening userScreening : bookmarkedUserScreenings) {
//            LocalDateTime screeningStartDate = userScreening.getScreening().getScreeningStartDate();
//
//            // 오늘이 screeningStartDate의 하루 전인 경우 해당 Screening을 가져옴
//            if (screeningStartDate.toLocalDate().isEqual(now.toLocalDate())) {
//                List<Screening> tomorrowScreenings = screeningAdaptor.findByStartDate(screeningStartDate);
//                Long userId = userScreening.getUser().getId();
//                List<NotificationRequest> notificationRequests = tomorrowScreenings.stream()
//                        .map(tomorrowScreening -> new NotificationRequest(tomorrowScreening, userId, NOTIFICATION_TITLE))
//                        .toList();
//                sendNotifications(notificationRequests);
//            }
//        }
//    }
//@Scheduled(cron = "0 0/1 * * * *")
//private void notifyReservation() {
//    LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
//    //LocalDateTime reservationTime = now.plusDays(1);
//
//    //userScreening에서 isBookMarked인 것들 중에서 user id, screening id가져와서 List<User> List<Screening>
//    //screening에서 startDate가져와서 startDate가 내일이면 알람을 보낼 수 있게 짜봐 fcm이랑 스프링 쓰고 있어
//
//    List<UserScreening> bookmarkedUserScreenings =  userScreeningAdaptor.findByBookMarked();
//
//    for (UserScreening userScreening : bookmarkedUserScreenings) {
//        LocalDateTime screeningStartDate = userScreening.getScreening().getScreeningStartDate();
//
//        // 오늘이 screeningStartDate의 하루 전인 경우 해당 Screening을 가져옴
//        if (screeningStartDate.toLocalDate().isEqual(now.toLocalDate())) {
//            List<Screening> tomorrowScreenings = screeningAdaptor.findByStartDate(screeningStartDate);
//            Long userId = userScreening.getUser().getId();
//            List<NotificationRequest> notificationRequests = tomorrowScreenings.stream()
//                    .map(tomorrowScreening -> new NotificationRequest(tomorrowScreening, userId, NOTIFICATION_TITLE))
//                    .toList();
//            sendNotifications(notificationRequests);
//        }
//    }
//}

    private void sendNotifications(List<NotificationRequest> requests) {
        for (NotificationRequest notificationRequest : requests) {
            // FCM을 사용하여 알림을 보내는 로직
            fcmService.sendMessageByToken(notificationRequest);
        }

    }


}
