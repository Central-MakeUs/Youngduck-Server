package com.example.api.screening.service;

import com.example.adaptor.UseCase;
import com.example.api.config.security.SecurityUtil;
import com.example.api.screening.dto.response.ScreeningResponse;
import com.example.domains.screening.adaptor.ScreeningAdaptor;
import com.example.domains.screening.entity.Screening;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@UseCase
@RequiredArgsConstructor
public class GetPastScreeningListUseCase {
    private final ScreeningAdaptor screeningAdaptor;
    public List<ScreeningResponse> execute() {
        Long userId = SecurityUtil.getCurrentUserId();

        List<Screening> screenings = screeningAdaptor.getBookmarkedScreenings(userId);
        List<ScreeningResponse> result = new ArrayList<>();
        for (Screening screening: screenings){
            result.add(ScreeningResponse.from(screening));
        }
        return result;
    }
}
