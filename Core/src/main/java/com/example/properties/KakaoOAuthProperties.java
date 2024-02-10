package com.example.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

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