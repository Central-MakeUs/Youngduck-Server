package com.example.error.exception;

import com.example.error.BaseErrorException;
import com.example.error.GlobalErrorCode;

public class NoAppleCodeException extends BaseErrorException {

    public static final BaseErrorException EXCEPTION = new NoAppleCodeException();

    private NoAppleCodeException() {
        super(GlobalErrorCode.NO_APPLE_CODE);
    }
}
