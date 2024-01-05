package com.example.error.exception;

import com.example.error.BaseErrorException;
import com.example.error.GlobalErrorCode;

public class InvalidTokenException extends BaseErrorException {

    public static final BaseErrorException EXCEPTION = new InvalidTokenException();

    private InvalidTokenException() {
        super(GlobalErrorCode.INVALID_TOKEN);
    }
}