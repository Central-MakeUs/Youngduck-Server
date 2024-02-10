package com.example.api.recommendedPopcorn.service;

import com.example.adaptor.UseCase;
import com.example.domains.recommendedPopcorn.adaptor.RecommendedPopcornAdaptor;
import com.example.domains.recommendedPopcorn.entity.RecommendedPopcorn;
import lombok.RequiredArgsConstructor;

import java.util.List;


@UseCase
@RequiredArgsConstructor
public class GetRecommendedRandomPopcornUseCase {
    private final RecommendedPopcornAdaptor recommendedPopcornAdaptor;
    public List<RecommendedPopcorn> execute() {
        List<RecommendedPopcorn> recommendedPopcornList = recommendedPopcornAdaptor.findByThreeIds();
        return recommendedPopcornList;
    }
}
