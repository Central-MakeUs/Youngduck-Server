package com.example.domains.diverseMovie.adaptor;

import com.example.adaptor.Adaptor;
import com.example.domains.diverseMovie.DiverseMovieRepository;
import com.example.domains.diverseMovie.entity.DiverseMovie;
import com.example.domains.diverseMovie.entity.dto.DiverseMovieResponseDto;
import com.example.domains.diverseMovie.entity.dto.QDiverseMovieResponseDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
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
        LocalDateTime now = LocalDateTime.now();

        // Get the start and end of the current week
        LocalDateTime startOfWeek = LocalDate.now().with(WeekFields.ISO.dayOfWeek(), 1).atStartOfDay();
        LocalDateTime endOfWeek = now;

        return jpaQueryFactory
                .select(new QDiverseMovieResponseDto(
                        diverseMovie.id,
                        diverseMovie.movieTitle,
                        diverseMovie.movieImgUrl,
                        diverseMovie.movieRank
                ))
                .from(diverseMovie)
                .where(diverseMovie.createdAt.between(startOfWeek, endOfWeek)) // Filter movies created within the current week
                .orderBy(diverseMovie.createdAt.asc())
                .limit(5)
                .fetch();
    }
}
