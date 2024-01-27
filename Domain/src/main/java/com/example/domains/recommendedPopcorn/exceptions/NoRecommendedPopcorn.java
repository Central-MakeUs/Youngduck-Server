package com.example.domains.recommendedPopcorn.exceptions;

import com.example.error.BaseErrorException;

public class NoRecommendedPopcorn extends BaseErrorException {
    public static final BaseErrorException EXCEPTION = new NoRecommendedPopcorn();

    private NoRecommendedPopcorn() {
        super(RecommendedErrorCode.NO_RECOMMENDED_POPCORN);
    }
}

