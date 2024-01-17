package com.example.api.screening.service;

import com.example.adaptor.UseCase;
import com.example.api.config.security.SecurityUtil;
import com.example.domains.screening.adaptor.ScreeningAdaptor;
import com.example.domains.userscreening.adaptor.UserScreeningAdaptor;
import com.example.domains.userscreening.entity.UserScreening;
import com.example.domains.userscreening.exception.exceptions.UserScreeningIsHost;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class PostScreeningPrivateUseCase {
    private final ScreeningAdaptor screeningAdaptor;
    private final UserScreeningAdaptor userScreeningAdaptor;
    public void execute(Long screeningId) {
        Long userId = SecurityUtil.getCurrentUserId();
        UserScreening userScreening = userScreeningAdaptor.findByUserAndScreening(userId,screeningId);
        validateUserScreening(userScreening);
        screeningAdaptor.changePrivateStatus(screeningId);
    }
    private void validateUserScreening(UserScreening userScreen) {
        //주최자인지 판단 (isHost)
        //존재하는 찜까지 되어있는 상태인지
        if (!userScreen.isHost() ) {
            throw UserScreeningIsHost.EXCEPTION;
        }

    }
}
