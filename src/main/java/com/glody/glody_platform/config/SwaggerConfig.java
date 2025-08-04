package com.glody.glody_platform.config;

import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI glodyOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Glody Study Abroad API")
                        .description("API documentation for Glody Platform")
                        .version("1.0.0"))
                .servers(Arrays.asList(
                        new Server().url("http://localhost:9876").description("Local Dev"),
                        new Server().url("https://pog.threemusketeer.click").description("Production")
                ))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .name("Authorization")
                        ));
    }
}
