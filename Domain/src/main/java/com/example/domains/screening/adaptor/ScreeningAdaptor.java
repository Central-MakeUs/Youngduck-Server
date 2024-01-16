package com.example.domains.screening.adaptor;

import com.example.adaptor.Adaptor;
import com.example.domains.screening.entity.Screening;
import com.example.domains.screening.repository.ScreeningRepository;
import com.example.domains.user.entity.User;
import com.example.domains.userscreening.entity.UserScreening;
import com.example.domains.userscreening.repository.UserScreeningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Adaptor
@RequiredArgsConstructor
public class ScreeningAdaptor {
    private final ScreeningRepository screeningRepository;
    private final UserScreeningRepository userScreeningRepository;

    public Screening save(Screening screening) {
        Screening savedScreening = screeningRepository.save(screening);
        return savedScreening;
    }

    public void saveUserScreening(UserScreening userScreening) {
        userScreeningRepository.save(userScreening);
    }

    public Screening findById(Long id) {
        Screening screening = screeningRepository.findById(id).get();
        return screening;
    }

    @Transactional
    public void changePrivateStatus(Long screeningId) {
       Screening screening = screeningRepository.findById(screeningId).get();
       screening.updatePrivacy(!screening.isPrivate());
    }
}
