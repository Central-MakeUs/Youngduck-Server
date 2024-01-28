package com.example.domains.userscreening.exception;

import com.example.dto.ErrorReason;
import com.example.error.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.example.consts.PopCornMateConsts.BAD_REQUEST;
import static com.example.consts.PopCornMateConsts.NOT_FOUND;

@Getter
@AllArgsConstructor
public enum UserScreeningErrorCode implements BaseErrorCode {
    USER_SCREENING_NOT_FOUND(NOT_FOUND, "USER_SCREENING_404_1", "유저의 스크리닝 정보를 찾을 수 없습니다."),
    USER_SCREENING_IS_HOST(NOT_FOUND, "USER_SCREENING_401", "호스트는 찜하기를 못합니다"),
    USER_SCREENING_IS_NOT_HOST(NOT_FOUND, "USER_SCREENING_401", "호스트가 아닙니다");
    private int status;
    private String code;
    private String reason;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.of(status, code, reason);
    }
}
