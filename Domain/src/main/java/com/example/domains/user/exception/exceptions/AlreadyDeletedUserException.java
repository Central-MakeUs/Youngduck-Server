package com.example.domains.user.exception.exceptions;

import com.example.domains.user.exception.UserErrorCode;
import com.example.error.BaseErrorException;

public class AlreadyDeletedUserException extends BaseErrorException {

    public static final BaseErrorException EXCEPTION = new AlreadyDeletedUserException();

    private AlreadyDeletedUserException() {
        super(UserErrorCode.USER_ALREADY_DELETED);
    }
}