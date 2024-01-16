package com.example.api.screening.service;

import com.example.adaptor.UseCase;
import com.example.api.config.security.SecurityUtil;
import com.example.domains.screening.adaptor.ScreeningAdaptor;
import com.example.domains.screening.entity.Screening;
import com.example.domains.userscreening.adaptor.UserScreeningAdaptor;
import com.example.domains.userscreening.entity.UserScreening;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@UseCase
@RequiredArgsConstructor
public class GetScreeningListUseCase {
    private final UserScreeningAdaptor userScreeningAdaptor;
    private final ScreeningAdaptor screeningAdaptor;
    //TODO dto 따로 만들기
    public List<Screening> execute(){
        Long userId = SecurityUtil.getCurrentUserId();

        //userScreening에서 찾기
        List<UserScreening> userScreenings = userScreeningAdaptor.findByUserId(userId);
        //userScreening에서 screeningId가지고 다 screening다 반환
        List<Screening> screenings = new ArrayList<>();

        for (UserScreening userScreening : userScreenings) {
            //isHost인 경우만 반환


            Long screeningId = userScreening.getScreening().getId();
            // Assuming there is a method findByScreeningId in your ScreeningAdaptor
            Screening screening = screeningAdaptor.findById(screeningId);

            // Check if screening is not null before adding to the list
            if (screening != null) {
                screenings.add(screening);
            }
        }
        return screenings;

    }
}
