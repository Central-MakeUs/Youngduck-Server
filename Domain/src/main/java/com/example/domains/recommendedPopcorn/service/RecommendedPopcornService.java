package com.example.domains.recommendedPopcorn.service;

import com.example.adaptor.DomainService;
import com.example.domains.recommendedPopcorn.adaptor.RecommendedPopcornAdaptor;
import com.example.domains.recommendedPopcorn.entity.RecommendedPopcorn;
import com.example.domains.recommendedPopcorn.repository.RecommendedPopcornRepository;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.transaction.annotation.Transactional;

@DomainService
@RequiredArgsConstructor
public class RecommendedPopcornService {
    private final RecommendedPopcornAdaptor recommendedPopcornAdaptor;
    private final RecommendedPopcornRepository recommendedPopcornRepository;

    @Transactional
    public void deleteLastWeekMovies() {
        recommendedPopcornAdaptor.deleteLastWeekRecommendations();
    }
}
