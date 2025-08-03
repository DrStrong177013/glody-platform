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
import java.nio.charset.StandardCharsets;

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

    public String buildStringToSign(PayosNotificationData data) {
        StringBuilder sb = new StringBuilder();
        sb.append("orderCode=").append(data.getOrderCode() == null ? "" : data.getOrderCode());
        sb.append("&amount=").append(data.getAmount() == null ? "" : data.getAmount());
        sb.append("&description=").append(data.getDescription() == null ? "" : data.getDescription());
        sb.append("&accountNumber=").append(data.getAccountNumber() == null ? "" : data.getAccountNumber());
        sb.append("&reference=").append(data.getReference() == null ? "" : data.getReference());
        sb.append("&transactionDateTime=").append(data.getTransactionDateTime() == null ? "" : data.getTransactionDateTime());
        sb.append("&currency=").append(data.getCurrency() == null ? "" : data.getCurrency());
        sb.append("&paymentLinkId=").append(data.getPaymentLinkId() == null ? "" : data.getPaymentLinkId());
        sb.append("&code=").append(data.getCode() == null ? "" : data.getCode());
        sb.append("&desc=").append(data.getDesc() == null ? "" : data.getDesc());
        sb.append("&counterAccountBankId=").append(data.getCounterAccountBankId() == null ? "" : data.getCounterAccountBankId());
        sb.append("&counterAccountBankName=").append(data.getCounterAccountBankName() == null ? "" : data.getCounterAccountBankName());
        sb.append("&counterAccountName=").append(data.getCounterAccountName() == null ? "" : data.getCounterAccountName());
        sb.append("&counterAccountNumber=").append(data.getCounterAccountNumber() == null ? "" : data.getCounterAccountNumber());
        sb.append("&virtualAccountName=").append(data.getVirtualAccountName() == null ? "" : data.getVirtualAccountName());
        sb.append("&virtualAccountNumber=").append(data.getVirtualAccountNumber() == null ? "" : data.getVirtualAccountNumber());
        return sb.toString();
    }

    private String hmacHex(String data, String key) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            byte[] raw = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : raw) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean validateSignature(PayosWebhookRequest webhookRequest) {
        PayosNotificationData d = webhookRequest.getData();
        String stringToSign = buildStringToSign(d);
        String calculatedSignature = hmacHex(stringToSign, "cc085211e7d008940e43cd45e807751c929fc1b34c403a396e037dbc56a28669"); // Phải là checksum key của bạn!
        return calculatedSignature.equalsIgnoreCase(webhookRequest.getSignature());
    }






}
