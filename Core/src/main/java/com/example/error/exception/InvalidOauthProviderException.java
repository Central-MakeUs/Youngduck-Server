package com.example.error.exception;

import com.example.error.BaseErrorException;
import com.example.error.GlobalErrorCode;

public class InvalidOauthProviderException extends BaseErrorException {

    public static final BaseErrorException EXCEPTION = new InvalidOauthProviderException();

    private InvalidOauthProviderException() {
        super(GlobalErrorCode.INVALID_OAUTH_PROVIDER);
    }
}
