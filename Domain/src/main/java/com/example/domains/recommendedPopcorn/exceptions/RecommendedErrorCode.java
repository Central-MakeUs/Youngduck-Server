package com.example.domains.recommendedPopcorn.exceptions;

import com.example.dto.ErrorReason;
import com.example.error.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum RecommendedErrorCode implements BaseErrorCode {
    DUPLICATE_MOVIE(409,"MOVIE_404", "중복된 영화가 존재합니다");
    private int status;
    private String code;
    private String reason;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.of(status, code, reason);
    }
}
