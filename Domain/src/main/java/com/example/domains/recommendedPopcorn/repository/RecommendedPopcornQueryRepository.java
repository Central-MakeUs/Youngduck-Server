package com.example.domains.recommendedPopcorn.repository;

import com.example.domains.recommendedPopcorn.entity.QRecommendedPopcorn;
import com.example.domains.recommendedPopcorn.entity.RecommendedPopcorn;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.domains.recommendedPopcorn.entity.QRecommendedPopcorn.recommendedPopcorn;

@Repository
@RequiredArgsConstructor
public class RecommendedPopcornQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Transactional
    public void incrementVoteCount(Long recommendedPopcorn) {
        QRecommendedPopcorn qRecommendedPopcorn = QRecommendedPopcorn.recommendedPopcorn;
        JPAUpdateClause updateClause = jpaQueryFactory.update(qRecommendedPopcorn);

        updateClause
                .set(qRecommendedPopcorn.recommendationCount, qRecommendedPopcorn.recommendationCount.add(1))
                .where(qRecommendedPopcorn.id.eq(recommendedPopcorn))
                .execute();
    }

}
