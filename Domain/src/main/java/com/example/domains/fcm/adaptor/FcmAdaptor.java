package com.example.domains.fcm.adaptor;

import com.example.adaptor.Adaptor;
import com.example.domains.fcm.entity.FcmToken;
import com.example.domains.fcm.repository.FcmRepository;
import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class FcmAdaptor {
    private final FcmRepository fcmRepository;

    public void save(FcmToken fcmToken ) {
        fcmRepository.save(fcmToken);
    }
}
