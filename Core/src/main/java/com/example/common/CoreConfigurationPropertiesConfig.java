package com.example.common;

import com.example.jwt.JwtProperties;
import com.example.properties.KakaoOAuthProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties({
        JwtProperties.class,
        KakaoOAuthProperties.class,
})
@Configuration
public class CoreConfigurationPropertiesConfig {}
