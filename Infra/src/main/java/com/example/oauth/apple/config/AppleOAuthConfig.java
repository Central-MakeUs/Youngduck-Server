package com.example.oauth.apple.config;

import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Import(AppleOAuthErrorDecoder.class)
public class AppleOAuthConfig {

    @Bean
    @ConditionalOnMissingBean(value = ErrorDecoder.class)
    public AppleOAuthErrorDecoder commonFeignErrorDecoder() {
        return new AppleOAuthErrorDecoder();
    }

    @Bean
    Encoder formEncoder() {
        return new feign.form.FormEncoder();
    }
}