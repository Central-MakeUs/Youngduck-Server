package com.example.domains.popcorn.adaptor;

import com.example.adaptor.Adaptor;
import com.example.domains.popcorn.entity.Popcorn;
import com.example.domains.popcorn.entity.QPopcorn;
import com.example.domains.popcorn.entity.dto.PopcornResponseDto;
import com.example.domains.popcorn.repository.PopcornRepository;
import com.example.domains.recommendedPopcorn.entity.QRecommendedPopcorn;
import com.example.domains.recommendedPopcorn.entity.RecommendedPopcorn;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;

import static com.example.domains.diverseMovie.entity.QDiverseMovie.diverseMovie;

@Adaptor
@RequiredArgsConstructor
public class PopcornAdaptor {
    private final PopcornRepository popcornRepository;
    private final JPAQueryFactory queryFactory;

    @Transactional
    public void save(Popcorn popcorn){
        popcornRepository.save(popcorn);
    }


    public void saveToPopcorn() {
        List<RecommendedPopcorn> listRecommendedPopcorn = findTopThree();
        for (RecommendedPopcorn rp : listRecommendedPopcorn) {
            Popcorn movie = convertToPopcorn(rp);
            save(movie);
        }
    }

    public Popcorn convertToPopcorn(RecommendedPopcorn rp){
        final Popcorn popcorn = Popcorn.of(rp.getMovieId(),rp.getMovieTitle(),rp.getImageUrl(),rp.getMovieDetail(),rp.getMovieDirector(),rp.getRecommendationReason(),rp.getRecommendationCount());
        return popcorn;
    }

    public List<RecommendedPopcorn> findTopThree() {
        QRecommendedPopcorn recommendedPopcorn = QRecommendedPopcorn.recommendedPopcorn;

        return queryFactory
                .selectFrom(recommendedPopcorn)
                .orderBy(
                        recommendedPopcorn.recommendationCount.desc(),
                        recommendedPopcorn.createdAt.desc()
                )
                .limit(3)
                .fetch();
    }

    public List<Popcorn> findLastWeekPopcorns() {
        QPopcorn popcorn = QPopcorn.popcorn;

        LocalDate today = LocalDate.now();
        LocalDate startOfLastWeek = today.minusDays(today.getDayOfWeek().getValue() + 6); // 지난 주의 월요일
        LocalDate endOfLastWeek = startOfLastWeek.plusDays(6); // 지난 주의 일요일

        return queryFactory
                .selectFrom(popcorn)
                .where(popcorn.createdAt.between(startOfLastWeek.atStartOfDay(), endOfLastWeek.atTime(23, 59, 59)))
                .orderBy(
                        popcorn.recommendationCount.desc(),
                        popcorn.createdAt.desc()
                )
                .limit(3)
                .fetch();
    }
}
