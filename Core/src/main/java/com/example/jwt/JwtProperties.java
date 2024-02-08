package com.example.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "auth.jwt")
public class JwtProperties {
    private String secretKey;
    private Long accessExp;
    private Long refreshExp;
}