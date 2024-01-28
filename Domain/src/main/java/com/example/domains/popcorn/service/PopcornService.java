package com.example.domains.popcorn.service;

import com.example.adaptor.DomainService;
import com.example.domains.popcorn.adaptor.PopcornAdaptor;
import com.example.domains.popcorn.entity.Popcorn;
import com.example.domains.recommendedPopcorn.entity.RecommendedPopcorn;
import lombok.RequiredArgsConstructor;

import java.util.List;

@DomainService
@RequiredArgsConstructor
public class PopcornService {
    private final PopcornAdaptor popcornAdaptor;
    public void getTopRecommended() {
        popcornAdaptor.saveToPopcorn();
    }
    public void getTopRecommendedTest(){
        popcornAdaptor.saveToPopcornTest();
    }
}
