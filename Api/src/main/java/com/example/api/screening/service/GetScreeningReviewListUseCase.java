package com.example.api.screening.service;

import com.example.adaptor.UseCase;
import com.example.api.config.security.SecurityUtil;
import com.example.domains.screening.entity.Screening;
import com.example.domains.screeningReview.adaptor.ReviewAdaptor;
import com.example.domains.screeningReview.entity.ScreeningReview;
import com.example.domains.screeningReview.entity.dto.ReviewResponseDto;
import com.example.domains.screeningReview.entity.dto.ScreeningWithReviewDto;
import com.example.domains.userscreening.adaptor.UserScreeningAdaptor;
import com.example.domains.userscreening.entity.UserScreening;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@UseCase
@RequiredArgsConstructor
public class GetScreeningReviewListUseCase {
    private final UserScreeningAdaptor userScreeningAdaptor;
    private final ReviewAdaptor screeningReviewAdaptor;

    public List<ScreeningWithReviewDto> execute(){
        Long userId = SecurityUtil.getCurrentUserId();

        //userScreening에서 찾기 (host)
        List<ScreeningWithReviewDto> userScreenings = screeningReviewAdaptor.getPostedScreeningReviews(userId);
//        //userScreening에서 screeningId가지고 다 screening다 반환
//        List<ScreeningReview> screeningReviews = new ArrayList<>();
//
//        for (ScreeningReview userScreening : userScreenings) {
//            Long screeningId = userScreening.getScreening().getId();
//            //isHost가 false인 것들 고르기
//            Screening screening = screeningReviewAdaptor.getPostedScreeningReview(screeningId);
//
//            // Check if screening is not null before adding to the list
//            if (screening != null) {
//                screenings.add(screening);
//            }
//        }
        return userScreenings;

    }
}
