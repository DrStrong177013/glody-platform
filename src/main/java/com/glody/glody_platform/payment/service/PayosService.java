package com.glody.glody_platform.payment.service;

import com.glody.glody_platform.payment.dto.CreatePaymentRequest;
import com.glody.glody_platform.payment.dto.CreatePaymentResponse;
import com.glody.glody_platform.payment.dto.PayosNotification;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

@Service
public class PayosService {
    private final WebClient client;
    private final String    clientId, apiKey, checksumKey;

    // **CHỈ** inject cái bean payosWebClient, KHÔNG inject Builder nữa
    public PayosService(@Qualifier("payosWebClient") WebClient payosWebClient,
                        @Value("${payos.client-id}")    String clientId,
                        @Value("${payos.api-key}")      String apiKey,
                        @Value("${payos.checksum-key}") String checksumKey) {
        this.client      = payosWebClient;
        this.clientId    = clientId;
        this.apiKey      = apiKey;
        this.checksumKey = checksumKey;
    }


    public CreatePaymentResponse createLink(Long orderCode, Long amount, String returnUrl, String cancelUrl){
        // build và sort data theo alphabet
        String data = "amount="+amount+
                "&cancelUrl="+cancelUrl+
                "&description=INV#"+orderCode+
                "&orderCode="+orderCode+
                "&returnUrl="+returnUrl;
        String sig = hmacHex(data, checksumKey);

        CreatePaymentRequest req = new CreatePaymentRequest();
        req.setOrderCode(orderCode);
        req.setAmount(amount);
        req.setDescription("INV#"+orderCode);
        req.setReturnUrl(returnUrl);
        req.setCancelUrl(cancelUrl);
        req.setSignature(sig);

        return client.post()
                .uri("/v2/payment-requests")
                .header("x-client-id", clientId)
                .header("x-api-key",   apiKey)
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
            for(byte b: raw) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch(Exception e){ throw new RuntimeException(e); }
    }

    public boolean verifyNotification(PayosNotification n){
        String data = "amount="+n.getAmount()+
                "&orderCode="+n.getOrderCode()+
                "&status="+n.getStatus();
        return hmacHex(data, checksumKey).equals(n.getSignature());
    }
}
