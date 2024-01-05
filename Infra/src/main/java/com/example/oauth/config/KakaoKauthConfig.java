package com.example.oauth.config;

import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Import(KakaoKauthErrorDecoder.class)
public class KakaoKauthConfig {

    @Bean
    @ConditionalOnMissingBean(value = ErrorDecoder.class)
    public KakaoKauthErrorDecoder commonFeignErrorDecoder() {
        return new KakaoKauthErrorDecoder();
    }

    @Bean
    Encoder formEncoder() {
        return new feign.form.FormEncoder();
    }
}
