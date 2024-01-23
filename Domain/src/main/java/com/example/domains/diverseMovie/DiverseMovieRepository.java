package com.example.domains.diverseMovie;

import com.example.domains.diverseMovie.entity.DiverseMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DiverseMovieRepository extends JpaRepository<DiverseMovie,Long> {
}
