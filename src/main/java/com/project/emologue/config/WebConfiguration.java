package com.project.emologue.config;

import com.project.emologue.model.user.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class WebConfiguration {

    @Autowired private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired private JwtExceptionFilter jwtExceptionFilter;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PATCH", "DELETE"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/v1/**", configuration);
        return source;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults())
                .authorizeHttpRequests(
                        (requests) -> requests
                                .requestMatchers(HttpMethod.POST, "/api/v1/users", "/api/*/users/authenticate",
                                        "/api/v1/admin", "/api/*/admin/authenticate")
                                .permitAll()
                                .requestMatchers(HttpMethod.GET,"/api/v1/jobs", "/api/v1/jobs/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v1/emotions", "/api/v1/emotions/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v1/diaries").authenticated()
                                .requestMatchers(HttpMethod.POST, "/api/v1/diaries/**").authenticated()
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/diaries/**").authenticated()
                                .requestMatchers(HttpMethod.GET, "/api/v1/diaries/admin").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.POST,"/api/v1/jobs").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.PATCH, "/api/v1/jobs/**").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/jobs/**").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/v1/emotions").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.PATCH, "/api/v1/emotions/**").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/emotions/**").hasAuthority("ROLE_ADMIN")
                                .anyRequest()
                                .authenticated())
                .sessionManagement(
                        (session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .csrf(CsrfConfigurer::disable)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtExceptionFilter, jwtAuthenticationFilter.getClass())
                .httpBasic(HttpBasicConfigurer::disable);
        return http.build();
    }
}
