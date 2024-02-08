package com.example.domains.recommendedPopcorn.exceptions;

import com.example.error.BaseErrorException;

public class DuplicateMovieId extends BaseErrorException {

    public static final BaseErrorException EXCEPTION = new DuplicateMovieId();

    private DuplicateMovieId() {
        super(RecommendedErrorCode.DUPLICATE_MOVIE);
    }
}
