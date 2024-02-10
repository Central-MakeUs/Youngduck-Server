package com.example.domains.block.exception;

import com.example.dto.ErrorReason;
import com.example.error.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum BlockExceptionErrorCode implements BaseErrorCode {
     DUPLICATE_REPORT(409,"BLOCK_409","신고는 한번만 할 수 있습니다");
    private int status;
    private String code;
    private String reason;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.of(status, code, reason);
    }
}