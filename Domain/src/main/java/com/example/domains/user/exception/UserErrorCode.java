package com.example.domains.user.exception;

import com.example.dto.ErrorReason;
import com.example.error.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.example.consts.PopCornMateConsts.BAD_REQUEST;
import static com.example.consts.PopCornMateConsts.NOT_FOUND;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements BaseErrorCode {
    FORBIDDEN_USER(BAD_REQUEST, "USER_400_1", "접근 제한된 유저입니다."),
    USER_ALREADY_SIGNUP(BAD_REQUEST, "USER_400_2", "이미 회원가입한 유저입니다."),
    USER_ALREADY_DELETED(BAD_REQUEST, "USER_400_3", "이미 지워진 유저입니다."),
    DUPLICATED_NICKNAME(BAD_REQUEST, "USER_400_4", "중복된 닉네임입니다."),

    USER_NOT_FOUND(NOT_FOUND, "USER_404_1", "유저 정보를 찾을 수 없습니다."),

    ALREADY_EXIST_SCRAP(BAD_REQUEST, "SCRAP_400_1", "이미 스크랩 되어있습니다."),

    SCRAP_NOT_FOUND(NOT_FOUND, "SCRAP_404_1", "스크랩 정보를 찾을 수 없습니다.");
    private int status;
    private String code;
    private String reason;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.of(status, code, reason);
    }
}