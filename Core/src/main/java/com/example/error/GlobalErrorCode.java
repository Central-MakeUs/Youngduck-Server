package com.example.error;

import com.example.dto.ErrorReason;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.example.consts.PopCornMateConsts.*;


@Getter
@AllArgsConstructor
public enum GlobalErrorCode implements BaseErrorCode {
    /** example * */
    EXAMPLE_ERROR(BAD_REQUEST, "GLOBAL_400_0", "에러 예시 입니다."),

    /** Server 오류 * */
    HTTP_MESSAGE_NOT_READABLE(BAD_REQUEST, "GLOBAL_400_1", "잘못된 형식의 값을 입력했습니다."),
    NO_APPLE_CODE(BAD_REQUEST, "GLOBAL_400_2", "애플 코드가 필요합니다."),
    EMPTY_PARAM_VALUE(BAD_REQUEST, "GLOBAL_400_3", "빈 파람 값을 입력했습니다."),
    _INTERNAL_SERVER_ERROR(INTERNAL_SERVER, "GLOBAL_500_1", "서버 오류. 관리자에게 문의 부탁드립니다."),
    INVALID_OAUTH_PROVIDER(INTERNAL_SERVER, "GLOBAL_500_2", "지원하지 않는 OAuth Provider 입니다."),
    SECURITY_CONTEXT_NOT_FOUND(INTERNAL_SERVER, "GLOBAL_500_3", "security context not found"),

    /** 토큰 에러 * */
    INVALID_TOKEN(UNAUTHORIZED, "AUTH_401_2", "올바르지 않은 토큰입니다."),
    INVALID_ACCESS_TOKEN_ERROR(UNAUTHORIZED, "AUTH_401_4", "알맞은 accessToken 을 넣어주세요."),
    EXPIRED_TOKEN(UNAUTHORIZED, "AUTH_401_3", "만료된 엑세스 토큰입니다"),
    EXPIRED_REFRESH_TOKEN(UNAUTHORIZED, "AUTH_403_1", "인증 시간이 만료되었습니다. 재 로그인 해주세요."),

    /** s3 오류 */
    S3_OBJECT_NOT_FOUND(BAD_REQUEST, "S3_500_1", "s3 객체가 존재하지 않습니다."),

    /** Feign Client 오류 */
    OTHER_SERVER_BAD_REQUEST(BAD_REQUEST, "FEIGN_400_1", "Other server bad request"),
    OTHER_SERVER_UNAUTHORIZED(BAD_REQUEST, "FEIGN_400_2", "Other server unauthorized"),
    OTHER_SERVER_FORBIDDEN(BAD_REQUEST, "FEIGN_400_3", "Other server forbidden"),
    OTHER_SERVER_EXPIRED_TOKEN(BAD_REQUEST, "FEIGN_400_4", "Other server expired token"),
    OTHER_SERVER_NOT_FOUND(BAD_REQUEST, "FEIGN_400_5", "Other server not found error"),
    ;

    private int status;
    private String code;
    private String reason;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.of(status, code, reason);
    }
}