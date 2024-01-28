package com.example.api.screening.service;

import com.example.adaptor.UseCase;
import com.example.api.screening.dto.response.ScreeningStatisticsResponse;
import com.example.domains.screening.adaptor.ScreeningAdaptor;
import com.example.domains.screening.entity.Screening;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class GetScreeningStatisticsUseCase {
    private final ScreeningAdaptor screeningAdaptor;
    public ScreeningStatisticsResponse execute(Long screeningId) {
        Screening screening = screeningAdaptor.findById(screeningId);

        return ScreeningStatisticsResponse.from(screening);
    }
}
