package com.glody.glody_platform.payment.controller;

import com.glody.glody_platform.common.AuthUtil;
import com.glody.glody_platform.payment.dto.CreateInvoiceRequestDto;
import com.glody.glody_platform.payment.dto.InvoiceRequestDto;
import com.glody.glody_platform.payment.dto.InvoiceResponseDto;
import com.glody.glody_platform.payment.dto.PaymentResponseDto;
import com.glody.glody_platform.payment.entity.Invoice;
import com.glody.glody_platform.payment.service.InvoiceQueryService;
import com.glody.glody_platform.payment.service.InvoiceService;
import com.glody.glody_platform.payment.service.PaymentQueryService;
import com.glody.glody_platform.security.JwtTokenUtil;
import com.glody.glody_platform.users.entity.User;
import com.glody.glody_platform.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final InvoiceQueryService invoiceQueryService;
    private final PaymentQueryService paymentQueryService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<InvoiceResponseDto> create(
            @RequestBody CreateInvoiceRequestDto dto,
            Authentication authentication) {

        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        InvoiceResponseDto result = invoiceService.createInvoice(dto, user.getId());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/me")
    public ResponseEntity<List<InvoiceResponseDto>> getMyInvoices(Authentication authentication) {
        String email = authentication.getName();
        Long userId = userRepository.findByEmail(email).map(User::getId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(invoiceQueryService.getMyInvoices(userId));
    }

    @GetMapping("/payments/me")
    public ResponseEntity<List<PaymentResponseDto>> getMyPayments(Authentication authentication) {
        String email = authentication.getName();
        Long userId = userRepository.findByEmail(email).map(User::getId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(paymentQueryService.getMyPayments(userId));
    }
}

