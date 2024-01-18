package com.example.domains.screening.validator;

import com.example.adaptor.Validator;
import com.example.domains.screening.entity.Screening;
import com.example.domains.screening.exception.exceptions.NotPassedDate;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Validator
@RequiredArgsConstructor
public class ScreeningValidator {

    public boolean checkDateValidation(Screening screening) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime screeningStartTime = screening.getScreeningStartDate();

        if(now.isAfter(screeningStartTime) || now == screeningStartTime) {
            return true;
        } else {
            throw NotPassedDate.EXCEPTION;
        }

    }
}
