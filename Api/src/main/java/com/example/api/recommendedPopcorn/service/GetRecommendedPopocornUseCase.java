package com.example.api.recommendedPopcorn.service;

import com.example.adaptor.UseCase;
import com.example.api.recommendedPopcorn.dto.response.RecommendedPopcornResponse;
import com.example.domains.recommendedPopcorn.adaptor.RecommendedPopcornAdaptor;
import com.example.domains.recommendedPopcorn.entity.RecommendedPopcorn;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class GetRecommendedPopocornUseCase {
    private final RecommendedPopcornAdaptor recommendedPopcornAdaptor;
    public List<RecommendedPopcorn> execute() {
            return recommendedPopcornAdaptor.findAllThisWeek();
    }
}
