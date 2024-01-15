package com.example.api.screening.service;

import com.example.adaptor.UseCase;
import com.example.api.config.security.SecurityUtil;
import com.example.api.screening.dto.response.BookMarkResponse;
import com.example.domains.screening.adaptor.ScreeningAdaptor;
import com.example.domains.screening.entity.Screening;
import com.example.domains.user.adaptor.UserAdaptor;
import com.example.domains.user.entity.User;
import com.example.domains.user.validator.UserValidator;
import com.example.domains.userscreening.adaptor.UserScreeningAdaptor;
import com.example.domains.userscreening.entity.UserScreening;
import com.example.domains.userscreening.exception.exceptions.UserScreeningIsHost;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class BookMarkScreeningUseCase {
    private final UserValidator userValidator;
    private final ScreeningAdaptor screeningAdaptor;
    private final UserScreeningAdaptor userScreeningAdaptor;
    private final UserAdaptor userAdaptor;

    public BookMarkResponse execute(Long screeningId) {
        Long userId = SecurityUtil.getCurrentUserId();
        validateExecution(userId);
        Screening screening = screeningAdaptor.findById(screeningId);
        BookMarkResponse bookMarkResult = bookMark(userId,screeningId);
        return bookMarkResult;
    }

    private BookMarkResponse bookMark(Long userId, Long screeningId) {
        //screening찾기
        Screening screening = screeningAdaptor.findById(screeningId);
        //userScreening에서 이미 존재하는지 판단
        //존재하는지 확인
        if(userScreeningAdaptor.checkExists(userId,screeningId)){
            //userScreeningValidator에서 주최자인지 아닌지 판단, 주최자면 error, 아니면 proceed
            UserScreening userScreening = userScreeningAdaptor.findByUserAndScreening(userId,screeningId);
            validateUserScreening(userScreening);
            UserScreening newUserScreeningWhenExists = updateWhenExists(userScreening);
            return BookMarkResponse.from(newUserScreeningWhenExists);
        };
        //특정 userScreening에서 update해주기
        UserScreening newUserScreening = update(screeningId,userId);
        //bookmarkResponse로 만들어주기
        return BookMarkResponse.firstCreated(newUserScreening);
    }

    private UserScreening updateWhenExists(UserScreening userScreening) {
        userScreeningAdaptor.updateUserScreening(userScreening);
        UserScreening newUserScreening = userScreeningAdaptor.findByUserAndScreening(userScreening.getUser().getId(),userScreening.getScreening().getId());
        return newUserScreening;
    }

    private UserScreening update(Long screeningId, Long userId) {
        User user = userAdaptor.findById(userId);
        Screening screening = screeningAdaptor.findById(screeningId);

        final UserScreening newUserScreening = UserScreening.of(
            false,
                true,
                true,
                user,
                screening
        );
        userScreeningAdaptor.save(newUserScreening);
        return newUserScreening;
    }

    private void validateUserScreening(UserScreening userScreening) {
        //주최자인지 판단 (isHost)
        if (userScreening.isHost()) {
            throw UserScreeningIsHost.EXCEPTION;
        }
    }

    private void validateExecution(Long userId) {
        userValidator.validateUserStatusNormal(userId);
    }
}
