package com.example.domains.diverseMovie.schedule;

//@SpringJUnitConfig(SchedulingConfig.class)
//@SpringBootTest
//@AllArgsConstructor
//public class DiverseMovieScheduleTest {
//
//    private final CountDownLatch latch = new CountDownLatch(2); // 2번 실행되면 테스트 성공으로 가정
//
//    private final DiverseMovieSchedule diverseMovieSchedule;
//
//    @Test
//    public void testScheduledMethod() throws InterruptedException {
//        // 테스트할 스케줄링 메소드를 호출
//        diverseMovieSchedule.scheduledMethod();
//
//        // 2번 실행되기를 기다림 (1번 실행 + 1번 스케줄링)
//        latch.await(2, TimeUnit.MINUTES);
//
//        // 테스트 성공 여부 확인
//        assertTrue(latch.getCount() == 0, "스케줄링이 2번 이상 실행되지 않았습니다.");
//    }
//}