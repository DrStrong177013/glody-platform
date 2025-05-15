package com.glody.glody_platform.payment.controller;

import com.glody.glody_platform.payment.dto.PaymentResultDto;
import com.glody.glody_platform.payment.entity.Invoice;
import com.glody.glody_platform.payment.entity.Payment;
import com.glody.glody_platform.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResultDto> processPayment(
            @RequestParam Long userId,
            @RequestParam Long packageId,
            @RequestParam(defaultValue = "VNPAY") String method) {
        PaymentResultDto result = paymentService.processPayment(userId, packageId, method);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/history")
    public List<Payment> getPayments(@RequestParam Long userId) {
        return paymentService.getPaymentHistory(userId);
    }

    @GetMapping("/invoices")
    public List<Invoice> getInvoices(@RequestParam Long userId) {
        return paymentService.getInvoiceHistory(userId);
    }


}
