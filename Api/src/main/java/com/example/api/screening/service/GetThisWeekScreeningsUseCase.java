package com.example.api.screening.service;

import com.example.adaptor.UseCase;
import com.example.domains.screening.adaptor.ScreeningAdaptor;
import com.example.domains.screening.entity.dto.ScreeningResponseDto;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class GetThisWeekScreeningsUseCase {
    private final ScreeningAdaptor screeningAdaptor;

    public List<ScreeningResponseDto> execute(){
        return screeningAdaptor.getTopThree();
    }
}
