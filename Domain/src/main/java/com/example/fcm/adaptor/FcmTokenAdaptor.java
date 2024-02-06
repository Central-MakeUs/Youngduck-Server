package com.example.fcm.adaptor;

import com.example.adaptor.Adaptor;
import com.example.fcm.repository.FcmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Adaptor
@RequiredArgsConstructor
public class FcmTokenAdaptor {
    private final FcmRepository fcmRepository;
    @Transactional
    public void execute(Long userId) {
        fcmRepository.deleteByUserId(userId);
    }

    public boolean findByUserId(Long userId) {
        return fcmRepository.existsById(userId);
    }
}
