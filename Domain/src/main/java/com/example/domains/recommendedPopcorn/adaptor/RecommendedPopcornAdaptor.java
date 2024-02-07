package com.example.domains.recommendedPopcorn.adaptor;

import com.example.adaptor.Adaptor;
import com.example.domains.recommendedPopcorn.entity.QRecommendedPopcorn;
import com.example.domains.recommendedPopcorn.entity.RecommendedPopcorn;
import com.example.domains.recommendedPopcorn.exceptions.DuplicateMovieId;
import com.example.domains.recommendedPopcorn.exceptions.NoRecommendedPopcorn;
import com.example.domains.recommendedPopcorn.repository.RecommendedPopcornRepository;
import com.example.domains.screening.entity.QScreening;
import com.example.domains.screening.entity.Screening;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Path;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Adaptor
@RequiredArgsConstructor
public class RecommendedPopcornAdaptor {
    private final RecommendedPopcornRepository recommendedPopcornRepository;
    private final JPAQueryFactory jpaQueryFactory;

    public RecommendedPopcorn save(RecommendedPopcorn recommendedPopcorn) {
        RecommendedPopcorn  savedRecommendedPopcorn = recommendedPopcornRepository.save(recommendedPopcorn);
        return savedRecommendedPopcorn;
    }

    public void checkExists(String movieId) {
        if(recommendedPopcornRepository.existsByMovieId(movieId)){
            throw DuplicateMovieId.EXCEPTION;
        };
    }
    public List<RecommendedPopcorn> findAllThisWeek() {
        QRecommendedPopcorn recommendedPopcorn = QRecommendedPopcorn.recommendedPopcorn;

        LocalDate today = LocalDate.now();
        LocalDate startOfThisWeek = today.with(DayOfWeek.MONDAY);
        LocalDate endOfThisWeek = today.with(DayOfWeek.SUNDAY);

        return jpaQueryFactory
                .selectFrom(recommendedPopcorn)
                .where(
                        recommendedPopcorn.createdAt.between(startOfThisWeek.atStartOfDay(), endOfThisWeek.atTime(23, 59, 59))
                )
                .orderBy(
                        recommendedPopcorn.createdAt.asc()
                )
                .fetch();
    }

    @Transactional
    public void incrementVoteCount(Long recommendedPopcorn) {
        QRecommendedPopcorn qRecommendedPopcorn = QRecommendedPopcorn.recommendedPopcorn;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qRecommendedPopcorn);

        updateClause
                .set(qRecommendedPopcorn.recommendationCount, qRecommendedPopcorn.recommendationCount.add(1))
                .where(qRecommendedPopcorn.id.eq(recommendedPopcorn))
                .execute();
    }

    public List<RecommendedPopcorn> findByThreeIds() {
        List<RecommendedPopcorn> result = new ArrayList<>();
        List<RecommendedPopcorn> thisWeekList = findAllThisWeek();

        if(thisWeekList.size()<=3) {
            return thisWeekList;
        }
        Set<Long> numberArray = generate(thisWeekList);
        System.out.println(numberArray.size());
        for (Long number : numberArray) {
            Optional<RecommendedPopcorn> foundItem = recommendedPopcornRepository.findById(number);

            // findById에서 가져온 값이 존재할 경우에만 result에 추가
            foundItem.ifPresent(result::add);
        }
        return result;
    }
    public Set<Long> generate(List<RecommendedPopcorn> thisWeekList) {
        Set<Long> arr = new HashSet<>();
        Random random = new Random();
        while (arr.size()!=3) {
            long randomIndex = ThreadLocalRandom.current().nextLong(thisWeekList.get(0).getId(), recommendedPopcornRepository.findAll().size() + 1);
            arr.add(randomIndex);
        }
        return arr;
    }

    @Transactional
    public void deleteLastWeekRecommendations(){// 오늘 날짜를 가져옵니다.
        // 오늘 날짜를 가져옵니다.
        LocalDate today = LocalDate.now();
        // 지난 주의 시작일과 종료일을 계산합니다.
        LocalDate lastWeekStart = today.minusWeeks(1).with(java.time.temporal.TemporalAdjusters.previous(java.time.DayOfWeek.MONDAY));
        LocalDate lastWeekEnd = lastWeekStart.plusDays(6); // 지난 주의 시작일에서 6일을 더하여 종료일을 설정합니다.

        // LocalDateTime으로 변환
        LocalDateTime lastWeekStartDateTime = lastWeekStart.atStartOfDay();
        LocalDateTime lastWeekEndDateTime = lastWeekEnd.atTime(23, 59, 59);

        // QueryDSL을 사용하여 지난 주에 생성된 것들의 "movieId"를 null로 업데이트합니다.
        jpaQueryFactory.update(QRecommendedPopcorn.recommendedPopcorn)
                .where(QRecommendedPopcorn.recommendedPopcorn.createdAt.between(lastWeekStartDateTime, lastWeekEndDateTime))
                .set(QRecommendedPopcorn.recommendedPopcorn.movieId, "null")
                .execute();
    }


    //TODO 로직 다시
    private void validateNumber(Long randomIndex, List<Long> arr) {
        if(arr.contains(randomIndex)) {
            throw NoRecommendedPopcorn.EXCEPTION;
        }
    }


//    private void validateRecommendedPopcorn() {
//        if(recommendedPopcornRepository.findAll().size() == 0) {
//            throw NoRecommendedPopcorn.EXCEPTION;
//        }
//    }
}
