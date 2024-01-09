package com.example.error.exception;

import com.example.error.BaseErrorException;
import com.example.error.GlobalErrorCode;

public class S3NotFoundException extends BaseErrorException {

    public static final BaseErrorException EXCEPTION = new S3NotFoundException();

    private S3NotFoundException() {
        super(GlobalErrorCode.S3_OBJECT_NOT_FOUND);
    }
}
