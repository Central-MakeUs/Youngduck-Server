package com.example.domains.recommendedPopcorn.service;

import com.example.adaptor.DomainService;
import com.example.domains.recommendedPopcorn.adaptor.RecommendedPopcornAdaptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DomainService
@RequiredArgsConstructor
public class RecommendedPopcornService {
    private final RecommendedPopcornAdaptor recommendedPopcornAdaptor;

    @Transactional
    public void deleteLastWeekMovies() {
        recommendedPopcornAdaptor.deleteLastWeekRecommendations();
    }

}
