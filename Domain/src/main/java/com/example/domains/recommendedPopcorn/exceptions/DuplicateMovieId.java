package com.example.domains.recommendedPopcorn.exceptions;

import com.example.domains.screening.exception.ScreeningErrorCode;
import com.example.domains.screening.exception.exceptions.NotPassedDate;
import com.example.error.BaseErrorException;

public class DuplicateMovieId extends BaseErrorException {

    public static final BaseErrorException EXCEPTION = new DuplicateMovieId();

    private DuplicateMovieId() {
        super(RecommendedErrorCode.DUPLICATE_MOVIE);
    }
}
