package com.example.domains.global;

import com.example.domains.diverseMovie.repository.DiverseMovieQueryRepository;
import com.example.domains.diverseMovie.repository.DiverseMovieRepository;
import com.example.domains.diverseMovie.entity.DiverseMovie;
import com.example.domains.global.config.TestQueryDslConfig;
import com.example.domains.popcorn.repository.PopcornRepository;
import com.example.domains.popcornUser.repository.PopcornUserRepository;
import com.example.domains.recommendedPopcorn.adaptor.RecommendedPopcornAdaptor;
import com.example.domains.recommendedPopcorn.entity.RecommendedPopcorn;
import com.example.domains.recommendedPopcorn.repository.RecommendedPopcornQueryRepository;
import com.example.domains.recommendedPopcorn.repository.RecommendedPopcornRepository;
import com.example.domains.recommendedPopcorn.service.RecommendedPopcornService;
import com.example.domains.screening.repository.ScreeningRepository;
import com.example.domains.screeningReview.repository.ScreeningReviewRepository;
import jakarta.persistence.LockModeType;
import org.hibernate.exception.LockAcquisitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
@TestPropertySource(locations = {"classpath:application-test.yml"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({
        TestQueryDslConfig.class,
})
public abstract class RepositoryTestSupport {
    @Autowired
     protected ScreeningRepository screeningRepository;

    @Autowired
    protected ScreeningReviewRepository screeningReviewRepository;

    @Autowired
    protected PopcornRepository popcornRepository;

    @Autowired
    protected RecommendedPopcornRepository recommendedPopcornRepository;

    @Autowired
    protected PopcornUserRepository popcornUserRepository;

    @Autowired
    protected DiverseMovieRepository diverseMovieRepository;
    @Autowired
    protected DiverseMovieQueryRepository diverseMovieQueryRepository;


    @Autowired
    protected RecommendedPopcornQueryRepository recommendedPopcornQueryRepository;

    protected void createDiverseMovie() {

        DiverseMovie diverseMovie1 = diverseMovieRepository.save(
                DiverseMovie.builder()
                        .movieRank("1")
                        .movieTitle("unknown")
                        .build()
        );

    }

    // 동시에 저장하려고 하는 메서드
    protected void createRecommendedPopcorn(RecommendedPopcorn recommendedPopcorn) {
        recommendedPopcornRepository.save(recommendedPopcorn);
    }
//    @Transactional(isolation = Isolation.SERIALIZABLE)
//    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public synchronized void postLikeRecommendedPopcorn(final Long id) {
//       RecommendedPopcorn recommendedPopcorn1 = recommendedPopcornRepository.findById(id).orElseThrow();
//
//        recommendedPopcorn1.increase();
        recommendedPopcornQueryRepository.incrementVoteCount(id);
    }


//    @Lock(LockModeType.PESSIMISTIC_WRITE)
//    public void proceed(RecommendedPopcorn recommendedPopcorn1) {
//        recommendedPopcorn1.increase();
////        recommendedPopcorn1.increase();
////        System.out.println(Thread.currentThread());
////        System.out.println(recommendedPopcorn1.getRecommendationCount());
////        recommendedPopcornRepository.save(recommendedPopcorn1);
//        recommendedPopcornRepository.save(recommendedPopcorn1);
//    }
}
