package com.example.error.exception;

import com.example.error.BaseErrorException;
import com.example.error.GlobalErrorCode;

public class ServerExpiredTokenException extends BaseErrorException {

    public static final BaseErrorException EXCEPTION = new ServerExpiredTokenException();

    private ServerExpiredTokenException() {
        super(GlobalErrorCode.OTHER_SERVER_EXPIRED_TOKEN);
    }
}