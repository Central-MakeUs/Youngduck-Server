package com.example.error.exception;

import com.example.error.BaseErrorException;
import com.example.error.GlobalErrorCode;

public class ServerNotAuthorizedException extends BaseErrorException {

    public static final BaseErrorException EXCEPTION = new  ServerNotAuthorizedException();

    private ServerNotAuthorizedException() {
        super(GlobalErrorCode.OTHER_SERVER_UNAUTHORIZED);
    }
}