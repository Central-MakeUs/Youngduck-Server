package com.example.domains.user.validator;

import com.example.adaptor.Validator;
import com.example.domains.user.adaptor.UserAdaptor;
import com.example.domains.user.enums.OauthInfo;
import com.example.domains.user.exception.exceptions.AlreadySignUpUserException;
import com.example.domains.user.exception.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;

@Validator
@RequiredArgsConstructor
public class UserValidator {
    private final UserAdaptor userAdaptor;

    public void validUserCanRegister(OauthInfo oauthInfo) {
        if (!checkUserCanRegister(oauthInfo)) throw AlreadySignUpUserException.EXCEPTION;
    }

    public Boolean checkUserCanRegister(OauthInfo oauthInfo) {
        return !userAdaptor.exist(oauthInfo);
    }

    public void validateUserStatusNormal(Long userId) {
        userAdaptor.findById(userId).validateUserStateNormal();
    }

    public void validateExist(Long userId) {
        if (!userAdaptor.existsById(userId)) {
            throw UserNotFoundException.EXCEPTION;
        }
    }
}
