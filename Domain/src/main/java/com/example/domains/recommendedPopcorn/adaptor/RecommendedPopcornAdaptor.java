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

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

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
        if(recommendedPopcornRepository.findAll().size()<=3) {
            return recommendedPopcornRepository.findAll();
        }
        Set<Long> numberArray = generate();
        for (Long number : numberArray) {
            Optional<RecommendedPopcorn> foundItem = recommendedPopcornRepository.findById(number);

            // findById에서 가져온 값이 존재할 경우에만 result에 추가
            foundItem.ifPresent(result::add);
        }
        return result;
    }
    public Set<Long> generate() {
        Set<Long> arr = new HashSet<>();
        Random random = new Random();
        while (arr.size()!=3) {
            Long randomIndex = random.nextLong(1,recommendedPopcornRepository.findAll().size()+1);
            arr.add(randomIndex);
        }
        return arr;
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
