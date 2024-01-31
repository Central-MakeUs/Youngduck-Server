package com.example.api.config.security;

import com.example.error.exception.SecurityContextNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    public static Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            throw SecurityContextNotFoundException.EXCEPTION;
        }
        return Long.valueOf(authentication.getName());
    }
}