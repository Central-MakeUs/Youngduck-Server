package com.example.api.screening.service;

import com.example.adaptor.UseCase;
import com.example.api.config.security.SecurityUtil;
import com.example.domains.screening.adaptor.ScreeningAdaptor;
import com.example.domains.screening.entity.Screening;
import com.example.domains.screeningReview.validator.ReviewValidator;
import com.example.domains.user.adaptor.UserAdaptor;
import com.example.domains.user.entity.User;
import com.example.domains.user.validator.UserValidator;
import com.example.domains.userscreening.entity.UserScreening;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class ReviewUseCase {
    private final UserValidator userValidator;

    private final ReviewValidator reviewValidator;
    private final UserAdaptor userAdaptor;
    private final ScreeningAdaptor screeningAdaptor;

    public void execute(Long screenId) {
        Long userId = SecurityUtil.getCurrentUserId();
        validate(screenId,userId);
        //reviewUpload
        //save, userScreenId
    }

    private void validate(Long screenId, Long userId){
        //userid, screeningid를 가지고
        User user = userAdaptor.findById(userId);
        Screening screening = screeningAdaptor.findById(screenId);
        //userScreening가져오기
        //스크리닝에서 날짜 지났는지 여부를 validator에서 걸러주기 - screeningvalidator
        //주최자인지 아닌지 validate
        //실제로 찜을 했었는지 파악하기 - UserScreeningvalidator, userScreening반환하기
    }


    private void validateExecution(Long userId) {
        userValidator.validateUserStatusNormal(userId);
    }
}
