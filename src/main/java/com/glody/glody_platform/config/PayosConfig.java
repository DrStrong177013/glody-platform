package com.glody.glody_platform.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class PayosConfig {
    @Bean
    public WebClient payosWebClient(WebClient.Builder builder,
                                    @Value("${payos.base-url}") String baseUrl) {
        System.out.println("=== PayOS WebClient base-url = " + baseUrl);
        return builder
                .baseUrl(baseUrl)
                .filter((req, next) -> {
                    System.out.println(">> Requesting PayOS: " + req.url());
                    return next.exchange(req);
                })
                .build();
    }
}