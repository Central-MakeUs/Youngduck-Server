package com.example.domains.screeningReview.repository;

import com.example.domains.screeningReview.entity.ScreeningReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScreeningReviewRepository extends JpaRepository<ScreeningReview, Long> {
    boolean existsByUserScreeningId(Long id);
}
