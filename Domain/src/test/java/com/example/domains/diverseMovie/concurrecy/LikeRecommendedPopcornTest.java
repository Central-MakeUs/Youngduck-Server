package com.example.domains.diverseMovie.concurrecy;

import com.example.domains.global.RepositoryTestSupport;
import com.example.domains.recommendedPopcorn.entity.RecommendedPopcorn;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;

public class LikeRecommendedPopcornTest extends RepositoryTestSupport {

    private final int THREAD_COUNT = 100; // 동시에 작업할 스레드 수
    private final int TOTAL_LIKES = THREAD_COUNT * 5; // 기대되는 총 좋아요 수

//    @BeforeEach
//    @Transactional
//    public void setUp() {
//        // 각 테스트 메서드 실행 전에 새로운 RecommendedPopcorn 객체 생성
//        sharedRecommendedPopcorn = RecommendedPopcorn.builder()
//                .recommendationCount(0)
//                .recommendationReason("너무 잘생겼어요")
//                .movieId("ssss")
//                .movieDetail("sdfsf")
//                .movieDirector("sd")
//                .imageUrl("ds")
//                .build();
//        recommendedPopcornRepository.save(sharedRecommendedPopcorn);
//    }

    @Test
    public void testConcurrentLikeIncrease() throws InterruptedException {
        // given
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch countDownLatch = new CountDownLatch(THREAD_COUNT);

        // when

        for (int i = 0; i < THREAD_COUNT; i++) {
            executorService.submit(() -> {
                try {
                    System.out.println("test");
                    postLikeRecommendedPopcorn(107L);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await(); // 모든 스레드가 작업을 완료할 때까지 대기
        //final RecommendedPopcorn stock = recommendedPopcornRepository.findById(107L).orElseThrow();

        System.out.println("what will happen?");
        // then
        //Assertions.assertThat(stock.getRecommendationCount()).isEqualTo(TOTAL_LIKES); // 총 좋아요 수를 확인
    }
}
