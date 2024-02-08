package com.example.api.config;


import com.example.api.config.security.FilterConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;


@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig {

    private final FilterConfig filterConfig;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .disable()

                .cors()
                .disable()

                .csrf()
                .disable()

                .httpBasic()
                .disable()

                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().expressionHandler(expressionHandler());

        http.authorizeRequests()
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers( "/api/swagger-ui/**", "/api/v3/api-docs/**").permitAll()
                .requestMatchers("/api/**").authenticated().and().anonymous().disable();

        http.apply(filterConfig);


        return http.build();
    }

    @Bean
    public DefaultWebSecurityExpressionHandler expressionHandler() {
        DefaultWebSecurityExpressionHandler expressionHandler =
                new DefaultWebSecurityExpressionHandler();
        return expressionHandler;
    }
}
