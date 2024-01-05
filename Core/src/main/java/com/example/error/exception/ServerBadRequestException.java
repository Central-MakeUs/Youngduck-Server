package com.example.error.exception;

import com.example.error.BaseErrorException;
import com.example.error.GlobalErrorCode;

public class ServerBadRequestException extends BaseErrorException {

    public static final BaseErrorException EXCEPTION = new ServerBadRequestException();

    private ServerBadRequestException() {
        super(GlobalErrorCode.OTHER_SERVER_BAD_REQUEST);
    }
}