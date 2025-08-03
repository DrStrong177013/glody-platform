package com.glody.glody_platform.payment.controller;

import com.glody.glody_platform.payment.dto.CreateInvoiceRequestDto;
import com.glody.glody_platform.payment.dto.InvoiceResponseDto;
import com.glody.glody_platform.payment.dto.PayosWebhookRequest;
import com.glody.glody_platform.payment.service.PaymentService;
import com.glody.glody_platform.users.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    // Tạo hóa đơn + sinh link PayOS
    @PostMapping("/invoices")
    public ResponseEntity<InvoiceResponseDto> createInvoice(
            @RequestBody CreateInvoiceRequestDto dto,
            @AuthenticationPrincipal User currentUser
    ) {
        Long userId = currentUser.getId();
        InvoiceResponseDto res = paymentService.createInvoiceAndPayment(dto, userId);
        return ResponseEntity.ok(res);
    }

    // Nhận webhook từ PayOS
    @PostMapping("/webhook/payos")
    public ResponseEntity<?> handlePayosWebhook(@RequestBody PayosWebhookRequest webhookRequest) {
        boolean ok = paymentService.handlePayosWebhook(webhookRequest);
        if (ok) return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().body("Invalid signature");
    }
}