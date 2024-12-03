package com.unidebnotes.unideb_notes_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

////FOR UPDATE LATER ...MIGHT USE JWT
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users/signup", "/api/users/verify").permitAll() // Allow public access
                        .anyRequest().authenticated() // Require authentication for all other endpoints
                )
                .httpBasic(); // Enable basic authentication for other endpoints

        return http.build();
    }
}
