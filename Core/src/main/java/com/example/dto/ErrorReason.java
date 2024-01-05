package com.example.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ErrorReason {
    private final int status;
    private final String code;
    private final String reason;

    @Builder
    private ErrorReason(int status, String code, String reason) {
        this.status = status;
        this.code = code;
        this.reason = reason;
    }

    public static ErrorReason of(int status, String code, String reason) {
        return ErrorReason.builder().status(status).code(code).reason(reason).build();
    }
}