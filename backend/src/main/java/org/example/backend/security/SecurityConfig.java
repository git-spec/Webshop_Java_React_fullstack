package org.example.backend.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(a -> a
                // .requestMatchers("/api/dashboard").authenticated()
                .anyRequest().permitAll()
            ).logout( l -> l.logoutSuccessUrl("http://localhost:5173"))
            .oauth2Login(o -> o.defaultSuccessUrl("http://localhost:5173/dashboard"));

        return http.build();
    }
} 
