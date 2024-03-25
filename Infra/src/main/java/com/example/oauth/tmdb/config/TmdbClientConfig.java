package com.example.oauth.tmdb.config;


import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.HttpMessageConverterCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Import({TmdbErrorDecoder.class})
public class TmdbClientConfig {

        @Bean
        @ConditionalOnMissingBean(value = ErrorDecoder.class)
        public TmdbErrorDecoder commonFeignErrorDecoder() {
            return new TmdbErrorDecoder();
        }

        @Bean
        Encoder formEncoder() {
            return new feign.form.FormEncoder();
        }


}
