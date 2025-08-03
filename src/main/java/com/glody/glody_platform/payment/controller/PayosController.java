package com.glody.glody_platform.payment.controller;

import com.glody.glody_platform.payment.dto.CreatePaymentResponse;
import com.glody.glody_platform.payment.service.PayosService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payos")
public class PayosController {

    private final PayosService payosService;
    public PayosController(PayosService payosService) {
        this.payosService = payosService;
    }

    @PostMapping("/create")
    public CreatePaymentResponse create(
            @RequestParam Long orderCode,
            @RequestParam Long amount,
            @RequestParam String returnUrl,
            @RequestParam String cancelUrl) {
        return payosService.createLink(orderCode, amount, returnUrl, cancelUrl);
    }
}
