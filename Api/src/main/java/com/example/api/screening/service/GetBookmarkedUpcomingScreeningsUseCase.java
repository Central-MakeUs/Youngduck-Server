package com.example.api.screening.service;

import com.example.adaptor.UseCase;
import com.example.api.config.security.SecurityUtil;
import com.example.domains.screening.adaptor.ScreeningAdaptor;
import com.example.domains.screening.entity.Screening;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class GetBookmarkedUpcomingScreeningsUseCase {
    private final ScreeningAdaptor screeningAdaptor;
    public List<Screening> execute(){
        Long userId = SecurityUtil.getCurrentUserId();
        return screeningAdaptor.getUpcomingScreenings(userId);
    }
}
