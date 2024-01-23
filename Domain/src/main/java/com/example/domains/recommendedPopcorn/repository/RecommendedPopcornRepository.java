package com.example.domains.recommendedPopcorn.repository;

import com.example.domains.recommendedPopcorn.entity.RecommendedPopcorn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendedPopcornRepository extends JpaRepository<RecommendedPopcorn,Long> {
    boolean existsByMovieId(String movieId);

}
