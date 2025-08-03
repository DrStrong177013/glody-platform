package com.glody.glody_platform.payment.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import vn.payos.PayOS;
import vn.payos.type.Webhook;
import com.glody.glody_platform.payment.dto.CreatePaymentRequest;
import com.glody.glody_platform.payment.dto.CreatePaymentResponse;

@Service
public class PayosService {
    private final WebClient payosWebClient;
    private final PayOS payOS;
    private final String clientId;
    private final String apiKey;

    public PayosService(
            @Value("${payos.client-id}") String clientId,
            @Value("${payos.api-key}") String apiKey,
            @Value("${payos.checksum-key}") String checksumKey,
            @Value("${payos.base-url:https://api.payos.vn}") String payosBaseUrl
    ) {
        this.clientId = clientId;
        this.apiKey = apiKey;
        this.payosWebClient = WebClient.builder()
                .baseUrl(payosBaseUrl)
                .build();
        this.payOS = new PayOS(clientId, apiKey, checksumKey);
    }

    public CreatePaymentResponse createLink(Long orderCode, Long amount, String returnUrl, String cancelUrl) {
        CreatePaymentRequest req = new CreatePaymentRequest();
        req.setOrderCode(orderCode);
        req.setAmount(amount);
        req.setDescription("INV#" + orderCode);
        req.setReturnUrl(returnUrl);
        req.setCancelUrl(cancelUrl);

        return payosWebClient.post()
                .uri("/v2/payment-requests")
                .header("x-client-id", clientId)
                .header("x-api-key", apiKey)
                .bodyValue(req)
                .retrieve()
                .bodyToMono(CreatePaymentResponse.class)
                .block();
    }

    // Chỉ check chữ ký, trả về true/false
    public boolean validateWebhook(Webhook webhookRequest) {
        try {
            payOS.verifyPaymentWebhookData(webhookRequest);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }




}
