package com.example.error.exception;

import com.example.error.BaseErrorException;
import com.example.error.GlobalErrorCode;

public class ExpiredRefreshTokenException extends BaseErrorException {

    public static final BaseErrorException EXCEPTION = new ExpiredRefreshTokenException();

    private ExpiredRefreshTokenException() {
        super(GlobalErrorCode.EXPIRED_REFRESH_TOKEN);
    }
}