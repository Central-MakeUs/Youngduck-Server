package com.example.domains.userscreening.exception.exceptions;

import com.example.domains.userscreening.exception.UserScreeningErrorCode;
import com.example.error.BaseErrorException;

public class UserScreeningNotFound extends BaseErrorException {
    public static final BaseErrorException EXCEPTION = new UserScreeningNotFound();

    private UserScreeningNotFound() {
        super(UserScreeningErrorCode.USER_SCREENING_NOT_FOUND);
    }
}
