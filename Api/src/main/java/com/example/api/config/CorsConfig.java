package com.example.api.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;

@Configuration
@RequiredArgsConstructor
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        ArrayList<String> allowedOriginPatterns = new ArrayList<>();
        allowedOriginPatterns.add("http://localhost:3000");
        allowedOriginPatterns.add("http://localhost:8080");
        allowedOriginPatterns.add("http://3.35.246.60");
        allowedOriginPatterns.add("http://3.35.246.60:8080");
        allowedOriginPatterns.add("http://popcornmate.p-e.kr");
        allowedOriginPatterns.add("https://popcornmate.p-e.kr");
        allowedOriginPatterns.add("https://www.popcornmate.p-e.kr");

        String[] patterns = allowedOriginPatterns.toArray(String[]::new);
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedOriginPatterns(patterns)
                .allowCredentials(true)
                .maxAge(3600L);
    }
}