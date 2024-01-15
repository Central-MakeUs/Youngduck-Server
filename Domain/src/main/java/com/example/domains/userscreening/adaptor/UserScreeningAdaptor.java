package com.example.domains.userscreening.adaptor;

import com.example.adaptor.Adaptor;
import com.example.domains.user.entity.User;
import com.example.domains.user.repository.UserRepository;
import com.example.domains.userscreening.entity.UserScreening;
import com.example.domains.userscreening.exception.exceptions.UserScreeningNotFound;
import com.example.domains.userscreening.repository.UserScreeningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Adaptor
@RequiredArgsConstructor
public class UserScreeningAdaptor {
    private final UserScreeningRepository userScreeningRepository;

    public void save(UserScreening userScreening) {
        userScreeningRepository.save(userScreening);
    }

    public UserScreening findByUserAndScreening(Long userId, Long screeningId) {
        if (checkExists(userId,screeningId)) {
            return userScreeningRepository.findByUserIdAndScreeningId(userId,screeningId).get();
        } else {
            throw UserScreeningNotFound.EXCEPTION;
        }
    }

    
    public boolean checkExists(Long userId, Long screeningId) {
        Optional<UserScreening> userScreening = userScreeningRepository.findByUserIdAndScreeningId(userId,screeningId);
        if (userScreening.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public void updateUserScreening(UserScreening userScreening) {
        if(!userScreening.isBookmarked()){
            userScreening.updateBookmarkedAndParticipating(true,true);
        }else{
            userScreening.updateBookmarkedAndParticipating(false,true);
        }

    }
}