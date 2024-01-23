package com.example.domains.recommendedPopcornUser.adaptor;

import com.example.adaptor.Adaptor;
import com.example.domains.recommendedPopcornUser.entity.RecommendedPopcornUser;
import com.example.domains.recommendedPopcornUser.repository.RecommendedPopcornUserRepository;
import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class RecommendedPopcornUserAdaptor {
    private final RecommendedPopcornUserRepository recommendedPopcornUserRepository;

    public void save(RecommendedPopcornUser request) {
        recommendedPopcornUserRepository.save(request);
    }
}
