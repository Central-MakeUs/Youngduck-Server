package com.example.domains.screeningReview.repository;

import com.example.domains.screeningReview.entity.ScreeningReview;
import com.example.domains.userscreening.entity.UserScreening;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScreeningReviewRepository extends JpaRepository<ScreeningReview, Long> {
}
