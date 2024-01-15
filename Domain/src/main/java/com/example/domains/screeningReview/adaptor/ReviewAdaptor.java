package com.example.domains.screeningReview.adaptor;

import com.example.adaptor.Adaptor;
import com.example.domains.screeningReview.entity.ScreeningReview;
import com.example.domains.screeningReview.repository.ScreeningReviewRepository;
import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class ReviewAdaptor {
    private final ScreeningReviewRepository screeningReviewRepository;

    public void save(ScreeningReview screeningReview) {
        screeningReviewRepository.save(screeningReview);
    }
}
