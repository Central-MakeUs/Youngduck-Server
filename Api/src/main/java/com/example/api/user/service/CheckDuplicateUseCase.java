package com.example.api.user.service;

import com.example.adaptor.UseCase;
import com.example.api.user.model.dto.DuplicateCheckResponse;
import com.example.api.user.model.dto.UpdateUserInfoRequest;
import com.example.domains.user.adaptor.UserAdaptor;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class CheckDuplicateUseCase {
    private final UserAdaptor userAdaptor;
    public DuplicateCheckResponse execute(UpdateUserInfoRequest request) {
        if(userAdaptor.existsByNickname(request.getNickname())){
            return DuplicateCheckResponse.from(true);
        } else {
            return DuplicateCheckResponse.from(false);
        }
    }
}
