package com.glody.glody_platform.payment.controller;

import com.glody.glody_platform.payment.dto.CreateInvoiceRequestDto;
import com.glody.glody_platform.payment.dto.InvoiceResponseDto;
import com.glody.glody_platform.payment.service.InvoiceService;
import com.glody.glody_platform.users.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    /**
     * Tạo Invoice + sinh link PayOS, trả về checkoutUrl trong DTO.
     * Ví dụ gọi:
     * POST /api/invoices?userId=123
     * body = { "packageId": 5, "returnUrl": "...", "cancelUrl": "..." }
     */
    @PostMapping
    public ResponseEntity<InvoiceResponseDto> createInvoice(
            @RequestBody CreateInvoiceRequestDto dto,
            @AuthenticationPrincipal User currentUser
    ) {
        Long userId = currentUser.getId();
        InvoiceResponseDto res = invoiceService.createInvoice(dto, userId);
        return ResponseEntity.ok(res);
    }
}
