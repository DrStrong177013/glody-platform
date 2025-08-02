package com.glody.glody_platform.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class PayosConfig {

    @Value("${payos.base-url}")
    private String baseUrl;

    @Bean
    public WebClient payosWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl(baseUrl)
                .build();
    }
}
