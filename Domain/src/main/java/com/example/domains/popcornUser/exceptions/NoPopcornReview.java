package com.example.domains.popcornUser.exceptions;

import com.example.error.BaseErrorException;

public class NoPopcornReview extends BaseErrorException {
    public static final BaseErrorException EXCEPTION = new NoPopcornReview();

    private NoPopcornReview () {
        super(PopcornUserErrorCode.NO_POPCORN_REVIEW);
    }
}
