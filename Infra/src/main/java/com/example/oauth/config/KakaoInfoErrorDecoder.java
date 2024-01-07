package com.example.oauth.config;

import com.example.error.exception.ServerBadRequestException;
import com.example.error.exception.ServerExpiredTokenException;
import com.example.error.exception.ServerForbiddenException;
import com.example.error.exception.ServerNotAuthorizedException;
import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class KakaoInfoErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() >= 400) {
            switch (response.status()) {
                case 401:
                    throw ServerNotAuthorizedException.EXCEPTION;
                case 403:
                    throw ServerForbiddenException.EXCEPTION;
                case 419:
                    throw ServerExpiredTokenException.EXCEPTION;
                default:
                    throw ServerBadRequestException.EXCEPTION;
            }
        }

        return FeignException.errorStatus(methodKey, response);
    }
}