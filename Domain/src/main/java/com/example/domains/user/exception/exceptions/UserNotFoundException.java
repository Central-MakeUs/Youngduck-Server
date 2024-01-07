package com.example.domains.user.exception.exceptions;

import com.example.domains.user.exception.UserErrorCode;
import com.example.error.BaseErrorException;

public class UserNotFoundException extends BaseErrorException {

    public static final BaseErrorException EXCEPTION = new UserNotFoundException();

    private UserNotFoundException() {
        super(UserErrorCode.USER_NOT_FOUND);
    }
}