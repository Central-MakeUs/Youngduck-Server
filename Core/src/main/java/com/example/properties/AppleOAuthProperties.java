package com.example.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@RequiredArgsConstructor
@ConfigurationProperties("oauth.apple")
public class AppleOAuthProperties {
    private String baseUrl;
    private String clientId;
    private String webClientId;
    private String keyId;
    private String redirectUrl;
    private String teamId;
    private String webCallbackUrl;
    private String authKey;
}
