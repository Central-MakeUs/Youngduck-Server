package com.example.domains.popcorn.schedule;

import com.example.domains.popcorn.service.PopcornService;
import com.example.domains.recommendedPopcorn.entity.RecommendedPopcorn;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PopcornSchedule {
    private final PopcornService popcornService;

    // 매 주 일요일 0시 0분에 실행되도록 cron 설정
    @Scheduled(cron = "0 0 0 * * SUN")
    public void scheduledMethod() {
        popcornService.getTopRecommended();
        System.out.println("매 주 일요일에 실행되는 작업");
    }

//        @Scheduled(cron = "0 * * * * *")
//    public void scheduledMethod() {
//            popcornService.getTopRecommended();
//            System.out.println("1분마다 실행되는 작업");
//    }
}
