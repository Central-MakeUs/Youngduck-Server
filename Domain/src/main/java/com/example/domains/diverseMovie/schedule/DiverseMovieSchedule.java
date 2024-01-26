package com.example.domains.diverseMovie.schedule;

import com.example.domains.diverseMovie.service.DiverseMovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiverseMovieSchedule {
    private final DiverseMovieService diverseMovieService;
     //매 주 월요일 0시 0분에 실행되도록 cron 설정
    @Scheduled(cron = "0 0 0 * * MON")
    public void scheduledMethod() {
        // 월요일에 실행될 로직을 여기에 작성
        diverseMovieService.getTopRated();
        System.out.println("매 주 월요일에 실행되는 작업");
    }


//    @Scheduled(cron = "0 * * * * *")
//    public void scheduledMethodTest() {
//        // 1분마다 실행될 로직을 여기에 작성
//        diverseMovieService.getTopRated();
//        System.out.println("1분마다 실행되는 작업");
//    }
}
