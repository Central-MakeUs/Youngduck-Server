package com.example.domains.userscreening.exception.exceptions;

import com.example.domains.userscreening.exception.UserScreeningErrorCode;
import com.example.error.BaseErrorException;

public class UserScreeningIsNotHost extends BaseErrorException {
    public static final BaseErrorException EXCEPTION = new  UserScreeningIsNotHost();

    private UserScreeningIsNotHost() {
        super(UserScreeningErrorCode.USER_SCREENING_IS_HOST);
    }
}