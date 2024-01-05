package com.example.oauth.config;

import com.example.oauth.dto.KakaoKauthErrorResponse;
import feign.Response;

public class KakaoKauthErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        KakaoKauthErrorResponse body = KakaoKauthErrorResponse.from(response);

//        try {
//            KakaoKauthErrorCode kakaoKauthErrorCode =
//                    KakaoKauthErrorCode.valueOf(body.getErrorCode());
//            throw kakaoKauthErrorCode.getDynamicException();
//        } catch (IllegalArgumentException e) {
//            KakaoKauthErrorCode koeInvalidRequest = KakaoKauthErrorCode.KOE_INVALID_REQUEST;
//            throw koeInvalidRequest.getDynamicException();
//        }
    }
}
