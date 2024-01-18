package com.example.api.config.response;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.util.ContentCachingRequestWrapper;

import static com.example.consts.PopCornMateConsts.SwaggerPatterns;

@Slf4j
@RestControllerAdvice
public class ResponseAdviser implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(
            MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(
            Object body,
            MethodParameter returnType,
            MediaType selectedContentType,
            Class selectedConverterType,
            ServerHttpRequest request,
            ServerHttpResponse response) {
        HttpServletResponse servletResponse =
                ((ServletServerHttpResponse) response).getServletResponse();

        ContentCachingRequestWrapper servletRequest =
                new ContentCachingRequestWrapper(
                        ((ServletServerHttpRequest) request).getServletRequest());

        for (String swaggerPattern : SwaggerPatterns) {
            if (servletRequest.getRequestURL().toString().contains(swaggerPattern)) return body;
        }



        int status = servletResponse.getStatus();
        HttpStatus resolve = HttpStatus.resolve(servletResponse.getStatus());

        if (resolve == null) return body;

        if (resolve.is2xxSuccessful())
            return SuccessResponse.onSuccess(status, body);

        return body;
    }


}