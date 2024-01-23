package com.example.domains.diverseMovie.adaptor;

import com.example.adaptor.Adaptor;
import com.example.domains.diverseMovie.DiverseMovieRepository;
import com.example.domains.diverseMovie.entity.DiverseMovie;
import com.example.domains.diverseMovie.entity.dto.DiverseMovieResponseDto;
import com.example.domains.diverseMovie.entity.dto.QDiverseMovieResponseDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.domains.diverseMovie.entity.QDiverseMovie.diverseMovie;

@Adaptor
@RequiredArgsConstructor
public class DiverseMovieAdaptor {
    private final DiverseMovieRepository diverseMovieRepository;
    private final JPAQueryFactory jpaQueryFactory;

    public void save(DiverseMovie diverseMovie) {
        diverseMovieRepository.save(diverseMovie);
    }

    public List<DiverseMovieResponseDto> findTop5ByOrderByCreatedAtDesc() {
        return jpaQueryFactory
                .select(new QDiverseMovieResponseDto(
                        diverseMovie.movieTitle,
                        diverseMovie.movieImgUrl,
                        diverseMovie.movieRank
                        )) // Change "SomeRank" to your actual rank logic
                .from(diverseMovie)
                .orderBy(diverseMovie.createdAt.desc())
                .limit(5)
                .fetch();
    }

}
