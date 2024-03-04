package com.example.domains.global;

import com.example.domains.diverseMovie.repository.DiverseMovieQueryRepository;
import com.example.domains.diverseMovie.repository.DiverseMovieRepository;
import com.example.domains.diverseMovie.entity.DiverseMovie;
import com.example.domains.global.config.TestQueryDslConfig;
import com.example.domains.popcorn.repository.PopcornRepository;
import com.example.domains.popcornUser.repository.PopcornUserRepository;
import com.example.domains.screening.repository.ScreeningRepository;
import com.example.domains.screeningReview.repository.ScreeningReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

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
    protected PopcornUserRepository popcornUserRepository;

    @Autowired
    protected DiverseMovieRepository diverseMovieRepository;
    @Autowired
    protected DiverseMovieQueryRepository diverseMovieQueryRepository;



    protected void createDiverseMovie() {

        DiverseMovie diverseMovie1 = diverseMovieRepository.save(
                DiverseMovie.builder()
                        .movieRank("1")
                        .movieTitle("unknown")
                        .build()
        );

    }
}
