package com.example.api.screening.service;

import com.example.adaptor.UseCase;
import com.example.api.screening.dto.response.GetCountResponse;
import com.example.domains.screening.adaptor.ScreeningAdaptor;
import com.example.domains.screening.entity.Screening;
import com.example.domains.screening.entity.dto.ScreeningCountDto;
import com.example.domains.screeningReview.adaptor.ReviewAdaptor;
import com.example.domains.screeningReview.entity.ScreeningReview;
import com.example.domains.userscreening.adaptor.UserScreeningAdaptor;
import com.example.domains.userscreening.entity.UserScreening;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@UseCase
@RequiredArgsConstructor
public class GetRateCountUseCase {
    private final ScreeningAdaptor screeningAdaptor;
    private final UserScreeningAdaptor userScreeningAdaptor;
    private final ReviewAdaptor reviewAdaptor;
    public GetCountResponse execute(Long screeningId) {
        final ScreeningCountDto request = getScreeningCount(screeningId);
        return GetCountResponse.from(request);
    }

    public ScreeningCountDto getScreeningCount(Long screeningId) {
        Screening screening = screeningAdaptor.findById(screeningId);
        Long rate = getScreeningRate(screening);

        return ScreeningCountDto.builder()
                .locationCountNeg(screening.getLocationCountNeg())
                .locationCountPos(screening.getLocationCountPos())
                .movieReviewCountNeg(screening.getMovieReviewCountNeg())
                .movieReviewCountPos(screening.getMovieReviewCountPos())
                .serviceCountNeg(screening.getServiceCountNeg())
                .serviceCountPos(screening.getServiceCountPos())
                .screeningRate(rate)
                .build();

    }

    private Long getScreeningRate(Screening screening) {
        List<UserScreening> userScreenings = userScreeningAdaptor.findByScreeningId(screening.getId());
        int count = screening.getScreeningRate();
        int total = getTotal(userScreenings);

        if(total==0){
            return (long) 0.0;
        } {
            return (long) ((count / (double) total) * 100);
        }
    }

    //TODO 리팩토링!!!!
    private int getTotal(List<UserScreening> userScreenings) {
        List<ScreeningReview> reviews = reviewAdaptor.findAll();
        List<ScreeningReview> reviews1 = new ArrayList<>();
        for (UserScreening userScreening : userScreenings) {
            // ScreeningReview에서 userScreeningId가 있는지 확인
            for (ScreeningReview review : reviews) {
                if (review.getUserScreening().getId() == userScreening.getId()) {
                    // userScreeningId가 일치하는 경우 count 증가
                    reviews1.add(review);
                    // 필요에 따라 추가 작업을 수행할 수 있습니다.
                }
            }
        }
        return reviews1.size();
    }
}
