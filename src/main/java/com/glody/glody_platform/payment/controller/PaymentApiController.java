package com.glody.glody_platform.payment.controller;

import com.glody.glody_platform.config.VnPayConfig;
import com.glody.glody_platform.payment.dto.CreateInvoiceRequestDto;
import com.glody.glody_platform.payment.dto.InvoiceResponseDto;
import com.glody.glody_platform.payment.service.InvoiceService;
import com.glody.glody_platform.payment.service.PaymentProcessingService;
import com.glody.glody_platform.payment.service.VnPayService;
import com.glody.glody_platform.payment.utils.HMACUtil;
import com.glody.glody_platform.users.entity.User;
import com.glody.glody_platform.users.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PaymentApiController {

    @Autowired
    private VnPayService vnPayService;
    private final PaymentProcessingService paymentProcessingService;
    private final InvoiceService invoiceService;
    private final UserRepository userRepository;

    @PostMapping("/create-payment")
    public ResponseEntity<Map<String, String>> createPayment(
            @RequestBody CreateInvoiceRequestDto dto,
            Authentication authentication,
            HttpServletRequest request) throws UnsupportedEncodingException
    {

        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 👇 Tạo invoice
        InvoiceResponseDto invoice = invoiceService.createInvoice(dto, user.getId());

        // 👇 Tạo URL thanh toán
        String paymentUrl = vnPayService.createPaymentUrl(
                request,
                invoice.getTotalAmount().intValue(),
                invoice.getNote(),
                invoice.getCode() // dùng code invoice làm txnRef
        );

        return ResponseEntity.ok(Map.of(
                "paymentUrl", paymentUrl,
                "invoiceCode", invoice.getCode()
        ));
    }
//    @GetMapping("/create-payment")
//    public ResponseEntity<String> createPayment(HttpServletRequest request,
//                                                @RequestParam int amount,
//                                                @RequestParam String orderInfo) {
//        try {
//            String paymentUrl = vnPayService.createPaymentUrl(request, amount, orderInfo);
//            return ResponseEntity.ok(paymentUrl);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
//        }
//    }

    @GetMapping("/vnpay-ipn")
    public ResponseEntity<Map<String, String>> handleVnPayIPN(HttpServletRequest request) throws UnsupportedEncodingException {
        Map<String, String[]> paramMap = request.getParameterMap();
        Map<String, String> vnpParams = new HashMap<>();
        for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
            vnpParams.put(entry.getKey(), entry.getValue()[0]); // giữ raw value
        }

        String receivedHash = vnpParams.remove("vnp_SecureHash");
        vnpParams.remove("vnp_SecureHashType");

        if (!isValidChecksum(vnpParams, receivedHash)) {
            return ResponseEntity.ok(Map.of("RspCode", "97", "Message", "Invalid Checksum"));
        }

        try {
            paymentProcessingService.processVnPayReturn(vnpParams);
            return ResponseEntity.ok(Map.of("RspCode", "00", "Message", "Confirm Success"));
        } catch (RuntimeException ex) {
            return ResponseEntity.ok(Map.of("RspCode", "99", "Message", ex.getMessage()));
        }
    }

    private boolean isValidChecksum(Map<String, String> params, String receivedHash) {
        List<String> sortedKeys = new ArrayList<>(params.keySet());
        Collections.sort(sortedKeys);

        StringBuilder sb = new StringBuilder();
        for (String key : sortedKeys) {
            sb.append(key).append("=").append(params.get(key)).append("&");
        }
        if (sb.length() > 0) sb.setLength(sb.length() - 1);

        String calculatedHash = HMACUtil.hmacSHA512(VnPayConfig.vnp_HashSecret, sb.toString());
        return calculatedHash.equals(receivedHash);
    }


}
