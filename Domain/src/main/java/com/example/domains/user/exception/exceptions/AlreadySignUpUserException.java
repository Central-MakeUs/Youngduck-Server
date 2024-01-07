package com.example.domains.user.exception.exceptions;

import com.example.domains.user.exception.UserErrorCode;
import com.example.error.BaseErrorException;

public class AlreadySignUpUserException extends BaseErrorException {

    public static final BaseErrorException EXCEPTION = new AlreadySignUpUserException();

    private AlreadySignUpUserException() {
        super(UserErrorCode.USER_ALREADY_SIGNUP);
    }
}