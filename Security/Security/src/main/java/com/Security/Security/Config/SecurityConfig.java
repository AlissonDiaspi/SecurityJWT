package com.Security.Security.Config;

import com.Security.Security.Security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // DESATIVA CSRF
                .csrf(csrf -> csrf.disable())

                // LIBERA IFRAME PARA H2
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))

                // API STATELESS
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // REGRAS DE ACESSO
                .authorizeHttpRequests(auth -> auth
                        // H2 Console
                        .requestMatchers("/h2-console/**").permitAll()
                        // Endpoints de autenticação
                        .requestMatchers("/auth/**").permitAll()
                        // Home ou raiz pública (opcional)
                        .requestMatchers("/").permitAll()
                        // Demais endpoints requerem autenticação
                        .anyRequest().authenticated()
                )

                // JWT FILTER
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
