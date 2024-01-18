package com.example.domains.screening.exception;

import com.example.dto.ErrorReason;
import com.example.error.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.example.consts.PopCornMateConsts.NOT_FOUND;

@Getter
@AllArgsConstructor
public enum ScreeningErrorCode implements BaseErrorCode {
    NOT_PASSED_DATE(NOT_FOUND, "DATE_404", "날짜가 지나지 않았습니다");
    private int status;
    private String code;
    private String reason;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.of(status, code, reason);
    }
}
