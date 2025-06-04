package com.example.RealConnect.security;

import com.example.RealConnect.security.jwt.JWTFilter;
import com.example.RealConnect.security.jwt.JWTUtil;
import com.example.RealConnect.security.jwt.LoginFIlter;
import com.example.RealConnect.user.domain.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${front_url}")
    private String[] frontUrl;

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;
    private final ObjectMapper objectMapper;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http
                .csrf(csrf->csrf.disable());

        http
                .formLogin((formLogin) -> formLogin.disable());

        http
                .sessionManagement(sess -> sess
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http
                .httpBasic((httpBasic) -> httpBasic.disable());

        http
                .authorizeHttpRequests((authorizeRequests)-> authorizeRequests
                        .requestMatchers("/login","/api/register", "/api/refresh-token").permitAll()
                        .anyRequest().authenticated());
        http
                .cors((cors)->cors.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request)
                    {
                        CorsConfiguration config = new CorsConfiguration();
                        /*
                            프론트 url의 경우, ','로 구분되어 allowed리스트에 들어감
                         */
                        List<String> allowed = Arrays.asList(frontUrl);
                        config.setAllowedOrigins(allowed);

                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowCredentials(true);
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setMaxAge(3600L);
                        config.setExposedHeaders(Collections.singletonList("Authorization"));

                        return config;
                    }
                }));

        http
                .addFilterAt(new LoginFIlter(authenticationManager(authenticationConfiguration), jwtUtil, objectMapper),
                        UsernamePasswordAuthenticationFilter.class);
        http
                .addFilterBefore(new JWTFilter(jwtUtil), LoginFIlter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception
    {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
