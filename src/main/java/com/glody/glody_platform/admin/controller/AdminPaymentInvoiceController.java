package com.glody.glody_platform.admin.controller;

import com.glody.glody_platform.common.PageResponse;
import com.glody.glody_platform.payment.dto.AdminInvoiceResponseDto;
import com.glody.glody_platform.payment.dto.AdminPaymentResponseDto;
import com.glody.glody_platform.payment.service.InvoiceService;
import com.glody.glody_platform.payment.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "Admin Payment Invoice Controller", description = "Admin quản lý và kiểm tra thanh toán, hóa đơn")
public class AdminPaymentInvoiceController {
    private final PaymentService paymentService;
    private final InvoiceService invoiceService;

    @Operation(
            summary = "Lấy tất cả thanh toán (Admin)",
            description = "Lấy tất cả thanh toán của tất cả người dùng"
    )
    @GetMapping("/payments")
    public ResponseEntity<PageResponse<AdminPaymentResponseDto>> getAllPayments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageResponse<AdminPaymentResponseDto> response = paymentService.getAllAdmin(page, size);
        return ResponseEntity.ok(response);
    }


    @Operation(
            summary = "Lấy một thanh toán cụ thể (Admin)",
            description = "Lấy một thanh toán cụ thể qua id thanh toán"
    )
    @GetMapping("/payments/{id}")
    public ResponseEntity<?> getPaymentById(@PathVariable Long id) {
        AdminPaymentResponseDto dto = paymentService.getByIdAdmin(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }


    @Operation(
            summary = "Lấy tất cả thanh toán dựa trên userId (Admin)",
            description = "Lấy tất cả thanh toán dựa trên userId"
    )
    @GetMapping("/payments/by-user/{userId}")
    public ResponseEntity<PageResponse<AdminPaymentResponseDto>> getPaymentsByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageResponse<AdminPaymentResponseDto> response = paymentService.getAllByUserForAdmin(userId, page, size);
        return ResponseEntity.ok(response);
    }


    @Operation(
            summary = "Lấy tất cả hóa đơn (Admin)",
            description = "Lấy tất cả hóa đơn của tất cả người dùng"
    )
    @GetMapping("/invoices")
    public ResponseEntity<PageResponse<AdminInvoiceResponseDto>> getAllInvoices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageResponse<AdminInvoiceResponseDto> response = invoiceService.getAllAdmin(page, size);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Lấy một hóa đơn cụ thể (Admin)",
            description = "Lấy một hóa đơn cụ thể qua id hóa đơn"
    )
    @GetMapping("/invoices/{id}")
    public ResponseEntity<?> getInvoiceById(@PathVariable Long id) {
        AdminInvoiceResponseDto dto = invoiceService.getByIdAdmin(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }
    @Operation(
            summary = "Lấy tất cả hóa đơn dựa trên userId (Admin)",
            description = "Lấy tất cả hóa đơn dựa trên userId"
    )
    @GetMapping("/invoices//by-user/{userId}")
    public ResponseEntity<PageResponse<AdminInvoiceResponseDto>> getInvoicesByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageResponse<AdminInvoiceResponseDto> response = invoiceService.getAllByUserForAdmin(userId, page, size);
        return ResponseEntity.ok(response);
    }
}
