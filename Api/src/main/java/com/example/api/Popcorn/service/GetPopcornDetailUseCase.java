package com.example.api.Popcorn.service;

import com.example.adaptor.UseCase;
import com.example.api.Popcorn.dto.response.PopcornDetailResponse;
import com.example.domains.popcorn.adaptor.PopcornAdaptor;
import com.example.domains.popcorn.entity.Popcorn;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class GetPopcornDetailUseCase {
    private final PopcornAdaptor popcornAdaptor;
    public PopcornDetailResponse execute(Long id) {
       Popcorn popcorn =  popcornAdaptor.findById(id);
       return PopcornDetailResponse.from(popcorn);
    }
}
