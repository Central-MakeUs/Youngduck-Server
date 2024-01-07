package com.example.error.exception;

import com.example.error.BaseErrorException;
import com.example.error.GlobalErrorCode;

public class ServerForbiddenException extends BaseErrorException {

    public static final BaseErrorException EXCEPTION = new ServerForbiddenException();

    private ServerForbiddenException() {
        super(GlobalErrorCode.OTHER_SERVER_FORBIDDEN);
    }
}