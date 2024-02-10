package com.example.domains.diverseMovie;

import com.example.domains.diverseMovie.entity.DiverseMovie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiverseMovieRepository extends JpaRepository<DiverseMovie,Long> {
}
