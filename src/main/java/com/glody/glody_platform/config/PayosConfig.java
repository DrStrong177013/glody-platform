package com.glody.glody_platform.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import vn.payos.PayOS;

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
    @Bean
    public PayOS payOS(
            @Value("${payos.client-id}") String clientId,
            @Value("${payos.api-key}") String apiKey,
            @Value("${payos.checksum-key}") String checksumKey,
            @Value("${payos.partner-code:}") String partnerCode
    ) {
        return new PayOS(clientId, apiKey, checksumKey, partnerCode);
    }
}