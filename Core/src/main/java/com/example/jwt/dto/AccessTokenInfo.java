package com.example.jwt.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class AccessTokenInfo {
    private final Long userId;
    private final String role;
}
