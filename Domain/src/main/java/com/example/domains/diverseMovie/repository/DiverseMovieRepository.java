package com.example.domains.diverseMovie.repository;

import com.example.domains.diverseMovie.entity.DiverseMovie;
import com.google.api.client.util.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiverseMovieRepository extends JpaRepository<DiverseMovie,Long> {
    DiverseMovie findTopDiverseMovieByOrderByCreatedAtAsc();

}
