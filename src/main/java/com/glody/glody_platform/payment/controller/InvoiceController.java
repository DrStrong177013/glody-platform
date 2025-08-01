package com.glody.glody_platform.payment.controller;

import com.glody.glody_platform.payment.dto.CreateInvoiceRequestDto;
import com.glody.glody_platform.payment.dto.InvoiceResponseDto;
import com.glody.glody_platform.payment.dto.PaymentResponseDto;
import com.glody.glody_platform.payment.service.InvoiceQueryService;
import com.glody.glody_platform.payment.service.InvoiceService;
import com.glody.glody_platform.payment.service.PaymentQueryService;
import com.glody.glody_platform.users.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Invoice & Payment Controller", description = "Quản lý hóa đơn và thanh toán")
@Slf4j
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final InvoiceQueryService invoiceQueryService;
    private final PaymentQueryService paymentQueryService;

    /**
     * 🧾 Tạo mới một hóa đơn thanh toán cho user hiện tại
     */
    @Operation(summary = "Tạo hóa đơn mới")
    @PostMapping("/invoices")
    public ResponseEntity<InvoiceResponseDto> createInvoice(
            @RequestBody CreateInvoiceRequestDto dto,
            @AuthenticationPrincipal User currentUser
    ) {
        log.info("Creating invoice for userId={}", currentUser.getId());
        InvoiceResponseDto created = invoiceService.createInvoice(dto, currentUser.getId());
        return ResponseEntity.ok(created);
    }

    /**
     * 📄 Lấy tất cả hóa đơn của user hiện tại
     */
    @Operation(summary = "Lấy hóa đơn của chính mình",
    description = "ENUM : PENDING, PAID, FAILED, EXPIRED")
    @GetMapping("/invoices/me")
    public ResponseEntity<List<InvoiceResponseDto>> getMyInvoices(
            @AuthenticationPrincipal User currentUser
    ) {
        log.info("Fetching invoices for userId={}", currentUser.getId());
        List<InvoiceResponseDto> invoices = invoiceQueryService.getMyInvoices(currentUser.getId());
        return ResponseEntity.ok(invoices);
    }

    /**
     * 💳 Lấy danh sách thanh toán của user hiện tại
     */
    @Operation(summary = "Lấy thanh toán của chính mình",
    description = "ENUM : PENDING, SUCCESS, FAILED")
    @GetMapping("/payments/me")
    public ResponseEntity<List<PaymentResponseDto>> getMyPayments(
            @AuthenticationPrincipal User currentUser
    ) {
        log.info("Fetching payments for userId={}", currentUser.getId());
        List<PaymentResponseDto> payments = paymentQueryService.getMyPayments(currentUser.getId());
        return ResponseEntity.ok(payments);
    }
}
