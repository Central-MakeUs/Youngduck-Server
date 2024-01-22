package com.example.domains.recommendedPopcornUser.repository;


import com.example.domains.recommendedPopcornUser.entity.RecommendedPopcornUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendedPopcornUserRepository extends JpaRepository<RecommendedPopcornUser, Long>{
}
