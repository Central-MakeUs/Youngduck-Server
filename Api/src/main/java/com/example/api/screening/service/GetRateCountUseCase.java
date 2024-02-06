package com.example.api.screening.service;

import com.example.adaptor.UseCase;
import com.example.api.screening.dto.response.GetCountResponse;
import com.example.domains.screening.adaptor.ScreeningAdaptor;
import com.example.domains.screening.entity.Screening;
import com.example.domains.screening.entity.dto.ScreeningCountDto;
import com.example.domains.userscreening.adaptor.UserScreeningAdaptor;
import com.example.domains.userscreening.entity.UserScreening;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class GetRateCountUseCase {
    private final ScreeningAdaptor screeningAdaptor;
    private final UserScreeningAdaptor userScreeningAdaptor;
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
        System.out.println(count);
        int total = userScreenings.size() - 1;
        System.out.println(total);
        if(total==0){
            return (long) 0.0;
        } {
            System.out.println((long) ((count / (double) total) * 100));
            return (long) ((count / (double) total) * 100);
        }
    }
}
