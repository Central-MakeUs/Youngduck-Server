package com.example.api.screening.service;

import com.example.adaptor.UseCase;
import com.example.api.config.security.SecurityUtil;
import com.example.api.screening.dto.response.ScreeningStatisticsResponse;
import com.example.domains.screening.adaptor.ScreeningAdaptor;
import com.example.domains.screening.entity.Screening;
import com.example.domains.userscreening.adaptor.UserScreeningAdaptor;
import com.example.domains.userscreening.entity.UserScreening;
import com.example.domains.userscreening.exception.exceptions.UserScreeningIsNotHost;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class GetScreeningStatisticsUseCase {
    private final ScreeningAdaptor screeningAdaptor;
    private final UserScreeningAdaptor userScreeningAdaptor;
    public ScreeningStatisticsResponse execute(Long screeningId) {
        Long userId = SecurityUtil.getCurrentUserId();
        Screening screening = screeningAdaptor.findById(screeningId);
        validateScreeningAuthority(userId,screening);
        int totalCount = getCountFromUserScreening(screening);

        return ScreeningStatisticsResponse.from(screening,totalCount);
    }

    private int getCountFromUserScreening(Screening screening) {
        int count = userScreeningAdaptor.findByScreeningId(screening.getId()).size() - 1;
        return count;
    }

    private void validateScreeningAuthority(Long userId, Screening screening) {
        Long screeningId = screening.getId();
        UserScreening userScreening = userScreeningAdaptor.findByUserAndScreening(userId,screeningId);
        if (!userScreening.isHost()) {
            throw UserScreeningIsNotHost.EXCEPTION;
        }
    }
}
