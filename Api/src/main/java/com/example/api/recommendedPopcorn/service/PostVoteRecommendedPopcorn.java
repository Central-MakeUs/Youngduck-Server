package com.example.api.recommendedPopcorn.service;

import com.example.adaptor.UseCase;
import com.example.domains.recommendedPopcorn.adaptor.RecommendedPopcornAdaptor;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class PostVoteRecommendedPopcorn {
    private final RecommendedPopcornAdaptor recommendedPopcornAdaptor;
    public void execute(Long recommendedPopcorn) {
        recommendedPopcornAdaptor.incrementVoteCount(recommendedPopcorn);
    }
}
