package com.example.domains.diverseMovie.schedule;

import com.example.domains.diverseMovie.adaptor.DiverseMovieAdaptor;
import com.example.domains.diverseMovie.entity.DiverseMovie;
import com.example.domains.diverseMovie.entity.dto.DiverseMovieResponseDto;
import com.example.domains.global.RepositoryTestSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

public class DiverseMovieQueryRepositoryTest extends RepositoryTestSupport {

    @BeforeEach
    void setUp() {
        for (int i = 0; i < 15; i++) {
            createDiverseMovie();
        }
    }
    @DisplayName("추천 영화 최신순 3개 반환하는지")
    @Test
    void getTrending() {
        //given
        Long latestDiverseMovie = diverseMovieRepository.findTopDiverseMovieByOrderByCreatedAtAsc().getId();


        //when
        //diverseMovieQueryRepository만들어서 따로 ㄱㄱ 그럼 될듯
        List<DiverseMovieResponseDto> diverseMovies = diverseMovieQueryRepository.findTop5ByOrderByCreatedAtDesc();
        System.out.println(diverseMovies.size());

        Long first = diverseMovies.get(0).getMovieId();
        Long last = diverseMovies.get(4).getMovieId();
        System.out.println(diverseMovies.size());


        //then
        Assertions.assertThat(diverseMovies).hasSize(5);
        Assertions.assertThat(first).isEqualTo(latestDiverseMovie);


    }
}
