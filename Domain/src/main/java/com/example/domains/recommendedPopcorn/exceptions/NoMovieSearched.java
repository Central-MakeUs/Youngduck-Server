package com.example.domains.recommendedPopcorn.exceptions;

import com.example.error.BaseErrorException;

public class NoMovieSearched extends BaseErrorException {
    public static final BaseErrorException EXCEPTION = new NoMovieSearched();

    private NoMovieSearched () {
        super(RecommendedErrorCode.DUPLICATE_MOVIE);
    }
}
