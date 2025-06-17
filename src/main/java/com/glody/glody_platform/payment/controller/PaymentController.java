package com.glody.glody_platform.payment.controller;

import com.glody.glody_platform.payment.dto.PaymentResultDto;
import com.glody.glody_platform.payment.entity.Invoice;
import com.glody.glody_platform.payment.entity.Payment;
import com.glody.glody_platform.payment.service.PaymentService;
import com.glody.glody_platform.payment.service.VnPayService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    @Autowired
    private VnPayService vnPayService;

    @GetMapping("/create-payment")
    public ResponseEntity<String> createPayment(HttpServletRequest request,
                                                @RequestParam int amount,
                                                @RequestParam String orderInfo) {
        try {
            String paymentUrl = vnPayService.createPaymentUrl(request, amount, orderInfo);
            return ResponseEntity.ok(paymentUrl);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/payment-return")
    public ResponseEntity<String> paymentReturn(HttpServletRequest request) {
        Map<String, String[]> params = request.getParameterMap();
        // TODO: validate vnp_SecureHash again to confirm integrity
        String responseCode = request.getParameter("vnp_ResponseCode");
        if ("00".equals(responseCode)) {
            return ResponseEntity.ok("Thanh toán thành công ✔️");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Thanh toán thất bại ❌");
        }
    }
}

