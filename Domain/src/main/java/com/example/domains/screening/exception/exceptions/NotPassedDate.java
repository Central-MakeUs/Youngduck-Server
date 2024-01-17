package com.example.domains.screening.exception.exceptions;

import com.example.domains.screening.exception.ScreeningErrorCode;
import com.example.domains.user.exception.UserErrorCode;
import com.example.domains.user.exception.exceptions.AlreadySignUpUserException;
import com.example.error.BaseErrorException;

public class NotPassedDate extends BaseErrorException {

    public static final BaseErrorException EXCEPTION = new NotPassedDate();

    private NotPassedDate() {
        super(ScreeningErrorCode.NOT_PASSED_DATE);
    }
}
