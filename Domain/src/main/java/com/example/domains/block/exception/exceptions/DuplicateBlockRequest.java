package com.example.domains.block.exception.exceptions;

import com.example.domains.block.exception.BlockExceptionErrorCode;
import com.example.domains.screening.exception.ScreeningErrorCode;
import com.example.domains.screening.exception.exceptions.NotPassedDate;
import com.example.error.BaseErrorException;

public class DuplicateBlockRequest extends BaseErrorException {

    public static final BaseErrorException EXCEPTION = new DuplicateBlockRequest();

    private DuplicateBlockRequest() {
        super(BlockExceptionErrorCode.DUPLICATE_REPORT);
    }
}
