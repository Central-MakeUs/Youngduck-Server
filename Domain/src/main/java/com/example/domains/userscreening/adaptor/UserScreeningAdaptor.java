package com.example.domains.userscreening.adaptor;

import com.example.adaptor.Adaptor;
import com.example.domains.user.entity.User;
import com.example.domains.user.repository.UserRepository;
import com.example.domains.userscreening.entity.UserScreening;
import com.example.domains.userscreening.repository.UserScreeningRepository;
import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class UserScreeningAdaptor {
    private final UserScreeningRepository userScreeningRepository;

    public void save(UserScreening userScreening) {
        userScreeningRepository.save(userScreening);
    }
}
