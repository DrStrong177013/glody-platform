package com.glody.glody_platform.payment.controller;

import com.glody.glody_platform.payment.dto.PayosNotification;
import com.glody.glody_platform.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/webhook/payos")
@RequiredArgsConstructor
public class WebhookController {

    private final PaymentService paymentService;

    /**
     * PayOS sẽ POST về đây khi khách thanh toán xong.
     */
    @PostMapping
    public ResponseEntity<String> receiveNotification(
            @RequestBody PayosNotification notification
    ) {
        paymentService.handlePayosCallback(notification);
        // Trả về 200 OK để PayOS không retry
        return ResponseEntity.ok("OK");
    }
}