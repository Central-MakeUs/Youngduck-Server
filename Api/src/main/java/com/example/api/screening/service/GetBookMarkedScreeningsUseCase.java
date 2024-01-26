package com.example.api.screening.service;

import com.example.adaptor.UseCase;
import com.example.api.config.security.SecurityUtil;
import com.example.api.screening.dto.response.ScreeningResponse;
import com.example.domains.screening.entity.Screening;
import com.example.domains.userscreening.adaptor.UserScreeningAdaptor;
import com.example.domains.userscreening.entity.UserScreening;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@UseCase
@RequiredArgsConstructor
public class GetBookMarkedScreeningsUseCase {
    private final UserScreeningAdaptor userScreeningAdaptor;
    public List<ScreeningResponse> execute() {
        Long userId = SecurityUtil.getCurrentUserId();
        List<Screening> screenings = userScreeningAdaptor.findBookmarkedUserScreening(userId);
        List<ScreeningResponse> result = new ArrayList<>();

        for (Screening screening : screenings) {
            result.add(ScreeningResponse.from(screening));
        }
        return result;
    }
}
