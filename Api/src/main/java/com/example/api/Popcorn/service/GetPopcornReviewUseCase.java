package com.example.api.Popcorn.service;

import com.example.adaptor.UseCase;
import com.example.api.Popcorn.dto.response.PopcornMyReviewResponse;
import com.example.api.Popcorn.dto.response.PopcornReviewResponse;
import com.example.api.config.security.SecurityUtil;
import com.example.domains.popcorn.adaptor.PopcornAdaptor;
import com.example.domains.popcornUser.adaptor.PopcornUserAdaptor;
import com.example.domains.popcornUser.entity.PopcornUser;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@UseCase
@RequiredArgsConstructor
public class GetPopcornReviewUseCase {
    private final PopcornUserAdaptor popcornUserAdaptor;
    private final PopcornAdaptor popcornAdaptor;
    public List<PopcornReviewResponse> execute(Long popcornId) {
        List<PopcornUser> popcornUserList = popcornUserAdaptor.findAllByPopcornId(popcornId);
        List<PopcornReviewResponse> popcornReviewResponses = new ArrayList<>();
        for (PopcornUser popcornUser : popcornUserList) {
            final PopcornReviewResponse newPopcornReview = PopcornReviewResponse.from(popcornUser);
            popcornReviewResponses.add(newPopcornReview);
        }
        return popcornReviewResponses;
    }

    public List<PopcornMyReviewResponse> getMyReviews() {
        Long userId = SecurityUtil.getCurrentUserId();
        List<PopcornUser> popcornUserList = popcornUserAdaptor.findAllByUserId(userId);
        List<PopcornMyReviewResponse> popcornReviewResponses = new ArrayList<>();

        for (PopcornUser popcornUser : popcornUserList) {
            final PopcornMyReviewResponse newPopcornReview = PopcornMyReviewResponse.from(popcornUser);
            popcornReviewResponses.add(newPopcornReview);
        }
        return popcornReviewResponses;
    }

    public int getRate(Long popcornId) {
        List<PopcornUser> popcornUsers = popcornUserAdaptor.findAllByPopcornId(popcornId);
        int length = popcornUsers.size();

        if (length == 0) {
            return 0;
        }
        int totalNumber = popcornAdaptor.findById(popcornId).getPopcornRate();

        return Math.round((float) totalNumber / length);
    }
}
