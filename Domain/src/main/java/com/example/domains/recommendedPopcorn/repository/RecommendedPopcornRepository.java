package com.example.domains.recommendedPopcorn.repository;

import com.example.domains.recommendedPopcorn.entity.RecommendedPopcorn;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface RecommendedPopcornRepository extends JpaRepository<RecommendedPopcorn,Long> {
    boolean existsByMovieId(String movieId);
//    @Lock(LockModeType.PESSIMISTIC_READ)
//    Optional<RecommendedPopcorn> findById(final Long id);
    //@Transactional(isolation = Isolation.SERIALIZABLE)
    //@Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<RecommendedPopcorn> findById(@Param("id") Long recommendedPopcornId);

}
