package com.example.domains.userscreening.exception.exceptions;

import com.example.domains.userscreening.exception.UserScreeningErrorCode;
import com.example.error.BaseErrorException;

public class UserScreeningIsHost extends BaseErrorException {
    public static final BaseErrorException EXCEPTION = new UserScreeningIsHost();

    private UserScreeningIsHost() {
        super(UserScreeningErrorCode.USER_SCREENING_IS_HOST);
    }
}
