package com.project.emologue.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Emologue API",
                description = "백엔드 API 명세서",
                version = "v1"
        ),
        security = @SecurityRequirement(name = "bearerAuth")
)

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addSecurityItem(new io.swagger.v3.oas.models.security.SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .name("Authorization")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                        )
                );
    }

    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("user-api").pathsToMatch("/api/v1/users/**",
                        "/api/v1/jobs/user/**",
                        "/api/v1/emotions/user/**",
                        "/api/v1/diaries/user/**",
                        "/api/v1/statistics/user/**")
                .build();
    }

    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("admin-api")
                .pathsToMatch("/api/v1/admin/**",
                        "/api/v1/diaries/admin/**",
                        "/api/v1/jobs/admin/**",
                        "/api/v1/emotions/admin/**",
                        "/api/v1/statistics/admin/**")
                .build();
    }
}
