package com.example.domains.popcornUser.exceptions;

import com.example.dto.ErrorReason;
import com.example.error.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PopcornUserErrorCode  implements BaseErrorCode {
    DUPLICATE_POPCORN_REVIEW(409,"POPCORN_REVIEW_409", "중복된 리뷰가 존재합니다"),
    NO_POPCORN_REVIEW(404,"POPCORN_REVIEW_404" ,"팝콘이 존재하지 않습니다" );
    private int status;
    private String code;
    private String reason;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.of(status, code, reason);
    }
}
