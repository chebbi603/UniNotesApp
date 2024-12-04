package com.unidebnotes.unideb_notes_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Disable CSRF for simplicity during development
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/api/users/register", "/api/users/verify","/api/users/login","/api/users/logout", "/h2-console/**").permitAll() // Allow these without authentication
                        .anyRequest().authenticated() // Require authentication for everything else
                )
                .headers().frameOptions().disable(); // Disable frame options for H2 console access

        return http.build();
    }
}
