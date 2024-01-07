package com.example.api.config.security;

import com.example.error.GlobalErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.error.BaseErrorException;
import com.example.error.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@RequiredArgsConstructor
@Component
public class AccessDeniedFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;


    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (BaseErrorException e) {
            handleException(response, getErrorResponse(e));
        } catch (AccessDeniedException e) {
            ErrorResponse access_denied =
                    ErrorResponse.from(GlobalErrorCode.INVALID_ACCESS_TOKEN_ERROR.getErrorReason());
            handleException(response, access_denied);
        }
    }

    private ErrorResponse getErrorResponse(BaseErrorException e) {
        return ErrorResponse.from(e.getErrorReason());
    }

    private void handleException(HttpServletResponse response, ErrorResponse errorResponse)
            throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(errorResponse.getStatus());
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}