package com.example.api.Popcorn.service;

import com.example.adaptor.UseCase;
import com.example.api.config.security.SecurityUtil;
import com.example.domains.popcornUser.adaptor.PopcornUserAdaptor;
import lombok.RequiredArgsConstructor;


@UseCase
@RequiredArgsConstructor
public class PostPopcornReviewComplainUseCase {
    private final PopcornUserAdaptor popcornUserAdaptor;
    public void execute(Long reviewId) {
        Long userId = SecurityUtil.getCurrentUserId();
        popcornUserAdaptor.postComplain(reviewId,userId);
    }
}
