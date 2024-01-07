package com.example.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Getter
@Setter
@RequiredArgsConstructor
@ConfigurationProperties("oauth.kakao")
public class KakaoOAuthProperties {
    private String baseUrl;
    private String clientId;
    private String clientSecret;
    private String redirectUrl;
    private String appId;
    private String adminKey;
    private String webClientId;
    private String webAppId;
}