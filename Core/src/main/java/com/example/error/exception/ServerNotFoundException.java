package com.example.error.exception;

import com.example.error.BaseErrorException;
import com.example.error.GlobalErrorCode;

public class ServerNotFoundException extends BaseErrorException {

    public static final BaseErrorException EXCEPTION = new ServerNotFoundException();

    private ServerNotFoundException() {
        super(GlobalErrorCode.OTHER_SERVER_NOT_FOUND);
    }
}