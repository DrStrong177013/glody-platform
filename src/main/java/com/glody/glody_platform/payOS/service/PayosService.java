package com.glody.glody_platform.payOS.service;

import com.glody.glody_platform.payOS.dto.CreatePaymentRequest;
import com.glody.glody_platform.payOS.dto.CreatePaymentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Service
public class PayosService {

    private final WebClient client;
    private final String    clientId;
    private final String    apiKey;
    private final String    checksumKey;

    public PayosService(WebClient.Builder builder,
                        @Value("${payos.base-url}")    String baseUrl,
                        @Value("${payos.client-id}")    String clientId,
                        @Value("${payos.api-key}")      String apiKey,
                        @Value("${payos.checksum-key}") String checksumKey) {
        this.client      = builder.baseUrl(baseUrl).build();
        this.clientId    = clientId;
        this.apiKey      = apiKey;
        this.checksumKey = checksumKey;
    }

    /**
     * Tạo payment link đơn giản nhất với các trường bắt buộc.
     *
     * @param orderCode  mã đơn hàng của bạn (integer)
     * @param amount     số tiền (integer, VND)
     * @param returnUrl  URL PayOS sẽ redirect khi thanh toán thành công
     * @param cancelUrl  URL PayOS sẽ redirect khi khách hủy
     */
    public CreatePaymentResponse createLink(Long orderCode,
                                            Long amount,
                                            String returnUrl,
                                            String cancelUrl) {
        // 1) Khởi tạo DTO request
        CreatePaymentRequest req = new CreatePaymentRequest();
        req.setOrderCode(orderCode);
        req.setAmount(amount);
        req.setReturnUrl(returnUrl);
        req.setCancelUrl(cancelUrl);
        // Bạn có thể đổi mô tả tuỳ ý
        req.setDescription("Thanh toán đơn " + orderCode);

        // 2) Tính signature (hex lowercase) theo spec:
        //    sort alphabet: amount, cancelUrl, description, orderCode, returnUrl
        String dataToSign = "amount="    + amount
                + "&cancelUrl="   + cancelUrl
                + "&description=" + req.getDescription()
                + "&orderCode="   + orderCode
                + "&returnUrl="   + returnUrl;
        String signature = hmacSha256Hex(dataToSign, checksumKey);
        req.setSignature(signature);

        // 3) Gửi request lên PayOS
        return client.post()
                .uri("/v2/payment-requests")
                .header("x-client-id", clientId)
                .header("x-api-key",   apiKey)
                .bodyValue(req)
                .retrieve()
                .bodyToMono(CreatePaymentResponse.class)
                .block();
    }

    /** HMAC-SHA256 → hex string (lower-case) */
    private String hmacSha256Hex(String data, String key) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            byte[] rawHmac = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder(rawHmac.length * 2);
            for (byte b : rawHmac) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi tạo signature", e);
        }
    }
}
