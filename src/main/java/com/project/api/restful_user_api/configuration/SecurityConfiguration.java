package com.project.api.restful_user_api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(authorizeRequest ->
                authorizeRequest.requestMatchers("/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "swagger-ui/index.html")
                        .permitAll()
                        .anyRequest()
                        .authenticated());

        return httpSecurity.build();
    }
}