package com.example.domains.diverseMovie.concurrecy;

import com.example.domains.global.RepositoryTestSupport;
import com.example.domains.recommendedPopcorn.entity.RecommendedPopcorn;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CreateRecommendedPopcornTest extends RepositoryTestSupport {

    @Test
    void createRecommendedPopcornTest() throws InterruptedException  {
        // given
        final int threadCount = 2; // 스레드 개수를 조정할 수 있습니다.
        final ExecutorService executorService = Executors.newFixedThreadPool(2);
        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        // 공유할 RecommendedPopcorn 객체
        RecommendedPopcorn sharedRecommendedPopcorn = RecommendedPopcorn.builder()
                .recommendationCount(0)
                .recommendationReason("너무 잘생겼어요")
                .movieId("ssss")
                .movieDetail("sdfsf")
                .movieDirector("sd")
                .imageUrl("ds")
                .build();


        // when
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    createRecommendedPopcorn(sharedRecommendedPopcorn);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();

        // then
        // 예외가 발생하는지 확인합니다.
        Assertions.assertThat(recommendedPopcornRepository.count()).isEqualTo(2);
    }
}
