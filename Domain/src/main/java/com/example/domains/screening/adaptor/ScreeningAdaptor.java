package com.example.domains.screening.adaptor;

import com.example.adaptor.Adaptor;
import com.example.domains.screening.entity.Screening;
import com.example.domains.screening.repository.ScreeningRepository;
import com.example.domains.user.entity.User;
import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class ScreeningAdaptor {
    private final ScreeningRepository screeningRepository;

    public Screening save(Screening screening) {
        Screening savedScreening = screeningRepository.save(screening);
        return savedScreening;
    }
}
