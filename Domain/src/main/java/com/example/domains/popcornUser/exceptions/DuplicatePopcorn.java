package com.example.domains.popcornUser.exceptions;

import com.example.domains.recommendedPopcorn.exceptions.NoMovieSearched;
import com.example.domains.recommendedPopcorn.exceptions.RecommendedErrorCode;
import com.example.error.BaseErrorException;

public class DuplicatePopcorn extends BaseErrorException {
    public static final BaseErrorException EXCEPTION = new DuplicatePopcorn();

    private DuplicatePopcorn () {
        super(PopcornUserErrorCode.DUPLICATE_POPCORN_REVIEW);
    }
}
