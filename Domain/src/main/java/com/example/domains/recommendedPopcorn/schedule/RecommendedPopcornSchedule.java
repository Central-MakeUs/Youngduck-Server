package com.example.domains.recommendedPopcorn.schedule;

import com.example.domains.recommendedPopcorn.service.RecommendedPopcornService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecommendedPopcornSchedule {
    private final RecommendedPopcornService recommendedPopcornService;
    @Scheduled(cron = "0 0 1 * * MON")
    public void clearRecommendedMovies(){
        recommendedPopcornService.deleteLastWeekMovies();
    }

//    @Scheduled(cron = "0 0/1 * * * *")
//    public void clearRecommendedMovies(){
//        recommendedPopcornService.deleteLastWeekMovies();
//    }
}
