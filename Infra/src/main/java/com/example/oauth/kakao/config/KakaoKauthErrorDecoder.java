package com.example.oauth.kakao.config;

import com.example.oauth.kakao.dto.KakaoKauthErrorResponse;
import com.example.oauth.kakao.exception.KakaoKauthErrorCode;
import feign.Response;
import feign.codec.ErrorDecoder;

public class KakaoKauthErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        KakaoKauthErrorResponse body = KakaoKauthErrorResponse.from(response);

        try {
            KakaoKauthErrorCode kakaoKauthErrorCode =
                    KakaoKauthErrorCode.valueOf(body.getErrorCode());
            throw kakaoKauthErrorCode.getDynamicException();
        } catch (IllegalArgumentException e) {
            KakaoKauthErrorCode koeInvalidRequest = KakaoKauthErrorCode.KOE_INVALID_REQUEST;
            throw koeInvalidRequest.getDynamicException();
        }
    }
}
