package com.example.domains.block.repository;

import com.example.domains.block.entity.Block;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockRepository extends JpaRepository<Block,Long> {
    boolean existsByUserIdAndScreeningReviewId(Long userId, Long screeningReviewId);
}
