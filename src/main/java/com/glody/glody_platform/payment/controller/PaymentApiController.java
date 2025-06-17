package com.glody.glody_platform.payment.controller;

import com.glody.glody_platform.config.VnPayConfig;
import com.glody.glody_platform.payment.service.VnPayService;
import com.glody.glody_platform.payment.utils.HMACUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PaymentApiController {

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

    @GetMapping("/vnpay-ipn")
    public ResponseEntity<Map<String, String>> handleVnPayIPN(HttpServletRequest request) throws UnsupportedEncodingException {
        Map<String, String[]> paramMap = request.getParameterMap();
        Map<String, String> vnpParams = new HashMap<>();

        for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue()[0];
            vnpParams.put(key, URLDecoder.decode(value, StandardCharsets.UTF_8));
        }

        String receivedHash = vnpParams.get("vnp_SecureHash");
        vnpParams.remove("vnp_SecureHash");
        vnpParams.remove("vnp_SecureHashType");

        List<String> sortedKeys = new ArrayList<>(vnpParams.keySet());
        Collections.sort(sortedKeys);

        StringBuilder sb = new StringBuilder();
        for (String key : sortedKeys) {
            sb.append(key).append("=").append(vnpParams.get(key)).append("&");
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }

        String calculatedHash = HMACUtil.hmacSHA512(VnPayConfig.vnp_HashSecret, sb.toString());

        if (!calculatedHash.equals(receivedHash)) {
            return ResponseEntity.ok(Map.of("RspCode", "97", "Message", "Invalid Checksum"));
        }

        String txnRef = vnpParams.get("vnp_TxnRef");
        String amount = vnpParams.get("vnp_Amount");
        String status = vnpParams.get("vnp_TransactionStatus");
        String responseCode = vnpParams.get("vnp_ResponseCode");

        // TODO: kiểm tra txnRef, amount, trạng thái

        if ("00".equals(responseCode) && "00".equals(status)) {
            // TODO: update DB trạng thái thành công
            return ResponseEntity.ok(Map.of("RspCode", "00", "Message", "Confirm Success"));
        } else {
            // TODO: update DB trạng thái thất bại
            return ResponseEntity.ok(Map.of("RspCode", "00", "Message", "Confirm Failed Payment"));
        }
    }
}
