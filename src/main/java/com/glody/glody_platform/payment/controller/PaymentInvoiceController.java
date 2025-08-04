package com.glody.glody_platform.payment.controller;

import com.glody.glody_platform.common.ErrorResponse;
import com.glody.glody_platform.common.PageResponse;
import com.glody.glody_platform.common.exception.BusinessLogicException;
import com.glody.glody_platform.payment.dto.CreateInvoiceRequestDto;
import com.glody.glody_platform.payment.dto.InvoiceResponseDto;
import com.glody.glody_platform.payment.dto.UserInvoiceResponseDto;
import com.glody.glody_platform.payment.dto.UserPaymentResponseDto;
import com.glody.glody_platform.payment.service.InvoiceService;
import com.glody.glody_platform.payment.service.PaymentService;
import com.glody.glody_platform.users.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import vn.payos.type.Webhook;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Payment Invoice Controller", description = "Tạo hóa đơn, thanh toán (bằng PayOS)")
public class PaymentInvoiceController {
    private final PaymentService paymentService;
    private final InvoiceService invoiceService;


    @Operation(
            summary = "Tạo hóa đơn đồng thời tạo link thanh toán(PayOS)",
            description = "Thanh toán bản PRODUCTION nên sẽ MẤT TIỀN. Trước khi tạo link thanh toán thì API này sẽ kiểm tra gói của người dùng" +
                    " nếu gói và người dùng đều hợp lệ thì sẽ tạo link thanh toán cùng với hóa đơn."
    )
    @PostMapping("/invoices")
    public ResponseEntity<?> createInvoice(
            @RequestBody CreateInvoiceRequestDto dto,
            @AuthenticationPrincipal User currentUser
    ) {
        Long userId = currentUser.getId();
        try {
            InvoiceResponseDto res = paymentService.createInvoiceAndPayment(dto, userId);
            return ResponseEntity.ok(res);
        } catch (BusinessLogicException e) {
            // Trả log về cho frontend
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @Operation(
            summary = "Lấy một hóa đơn",
            description = "Lấy một hóa đơn cụ thể bằng id hóa đơn của người dùng"
    )
    @GetMapping("/invoices/{id}")
    public ResponseEntity<?> getMyInvoiceById(
            @PathVariable Long id,
            @AuthenticationPrincipal User currentUser
    ) {
        Long userId = currentUser.getId();
        UserInvoiceResponseDto dto = invoiceService.getByIdForUser(id, userId);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Lấy tất cả hóa đơn",
            description = "Lấy tất cả hóa đơn của người dùng"
    )
    @GetMapping("/invoices")
    public ResponseEntity<PageResponse<UserInvoiceResponseDto>> getAllMyInvoices(
            @AuthenticationPrincipal User currentUser,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Long userId = currentUser.getId();
        PageResponse<UserInvoiceResponseDto> response = invoiceService.getAllByUser(userId, page, size);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Lấy một thanh toán",
            description = "Lấy một thanh toán cụ thể bằng id thanh toán của người dùng"
    )
    @GetMapping("/payments/{id}")
    public ResponseEntity<?> getMyPaymentById(
            @PathVariable Long id,
            @AuthenticationPrincipal User currentUser
    ) {
        Long userId = currentUser.getId();
        UserPaymentResponseDto dto = paymentService.getByIdForUser(id, userId);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Lấy tất cả thanh toán",
            description = "Lấy tất cả thanh toán của người dùng"
    )
    @GetMapping("/payments")
    public ResponseEntity<PageResponse<UserPaymentResponseDto>> getAllMyPayments(
            @AuthenticationPrincipal User currentUser,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Long userId = currentUser.getId();
        PageResponse<UserPaymentResponseDto> response = paymentService.getAllByUser(userId, page, size);
        return ResponseEntity.ok(response);
    }


    @Operation(
            summary = "Webhook (PayOS)",
            description = "API này dành riêng cho phía PayOS dùng để thông báo và cập nhật thông tin thanh toán. " +
                    "API chứa logic cập nhật thanh toán, hóa đơn và xử lý gói đăng ký của người dùng sau khi " +
                    "thanh toán thành công."
    )
    @PostMapping("/webhook/payos")
    public ResponseEntity<?> handlePayosWebhook(@RequestBody Webhook webhookRequest) {
        boolean ok = paymentService.handlePayosWebhook(webhookRequest);
        if (ok) return ResponseEntity.ok().build();
        return ResponseEntity.badRequest()
                .body(new ErrorResponse("Signature is invalid or request is not trusted!"));
    }
}