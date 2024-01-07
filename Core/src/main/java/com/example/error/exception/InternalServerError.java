package com.example.error.exception;

import com.example.error.BaseErrorException;
import com.example.error.GlobalErrorCode;

public class InternalServerError extends BaseErrorException {

    public static final BaseErrorException EXCEPTION = new InternalServerError();

    private InternalServerError() {
        super(GlobalErrorCode._INTERNAL_SERVER_ERROR);
    }
}
