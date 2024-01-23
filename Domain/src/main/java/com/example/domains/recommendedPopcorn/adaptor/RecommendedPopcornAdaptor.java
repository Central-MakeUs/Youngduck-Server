package com.example.domains.recommendedPopcorn.adaptor;

import com.example.adaptor.Adaptor;
import com.example.domains.recommendedPopcorn.entity.QRecommendedPopcorn;
import com.example.domains.recommendedPopcorn.entity.RecommendedPopcorn;
import com.example.domains.recommendedPopcorn.exceptions.DuplicateMovieId;
import com.example.domains.recommendedPopcorn.repository.RecommendedPopcornRepository;
import com.example.domains.screening.entity.QScreening;
import com.example.domains.screening.entity.Screening;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public List<RecommendedPopcorn> findAll() {
        return recommendedPopcornRepository.findAll();
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
}
