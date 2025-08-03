package com.glody.glody_platform.payment.service;

import com.glody.glody_platform.payment.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

@Slf4j
@Service
@RequiredArgsConstructor
public class PayosService {
    private final WebClient payosWebClient;
    @Value("${payos.client-id}")
    private String clientId;
    @Value("${payos.api-key}")
    private String apiKey;
    @Value("${payos.checksum-key}")
    private String checksumKey;

    public CreatePaymentResponse createLink(Long orderCode, Long amount, String returnUrl, String cancelUrl) {
        String data = "amount=" + amount +
                "&cancelUrl=" + cancelUrl +
                "&description=INV#" + orderCode +
                "&orderCode=" + orderCode +
                "&returnUrl=" + returnUrl;
        String sig = hmacHex(data, checksumKey);

        CreatePaymentRequest req = new CreatePaymentRequest();
        req.setOrderCode(orderCode);
        req.setAmount(amount);
        req.setDescription("INV#" + orderCode);
        req.setReturnUrl(returnUrl);
        req.setCancelUrl(cancelUrl);
        req.setSignature(sig);

        return payosWebClient.post()
                .uri("/v2/payment-requests")
                .header("x-client-id", clientId)
                .header("x-api-key", apiKey)
                .bodyValue(req)
                .retrieve()
                .bodyToMono(CreatePaymentResponse.class)
                .block();
    }

    private String hmacHex(String data, String key) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(key.getBytes(), "HmacSHA256"));
            byte[] raw = mac.doFinal(data.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : raw) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean validateSignature(PayosWebhookRequest webhookRequest) {
        PayosNotificationData d = webhookRequest.getData();
        String dataToSign =
                "orderCode=" + d.getOrderCode() +
                        "&amount=" + d.getAmount() +
                        "&description=" + d.getDescription() +
                        "&reference=" + d.getReference() +
                        "&status=" + d.getCode();
        String calculatedSignature = hmacHex(dataToSign, checksumKey);
        return calculatedSignature.equalsIgnoreCase(webhookRequest.getSignature());
    }
}
