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
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults())
                .authorizeHttpRequests(requests -> requests
                        // Swagger 허용
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/v3/api-docs",
                                "/api-docs/**",
                                "/api-docs",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        // 회원가입/로그인 관련
                        .requestMatchers(HttpMethod.POST,
                                "/api/v1/users",
                                "/api/v1/users/authenticate",
                                "/api/v1/admin",
                                "/api/v1/admin/authenticate"
                        ).permitAll()

                        // 공개 API
                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/jobs/user",
                                "/api/v1/jobs/user/**"
                        ).permitAll()

                        // 인증 필요
                        .requestMatchers(HttpMethod.GET, "/api/v1/diaries/user").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/v1/diaries/user/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/diaries/user/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/v1/statistics/user/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/v1/emotions/user").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/v1/emotions/user/**").authenticated()

                        // 관리자 전용
                        .requestMatchers(HttpMethod.GET, "/api/v1/statistics/admin/jobs").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/diaries/admin").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/jobs/admin/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/jobs/admin/").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/jobs/admin/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/jobs/admin/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/emotions/admin/").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/emotions/admin/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/emotions/admin/**").hasAuthority("ROLE_ADMIN")

                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(CsrfConfigurer::disable)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtExceptionFilter, jwtAuthenticationFilter.getClass())
                .httpBasic(HttpBasicConfigurer::disable);

        return http.build();
    }
}
