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
import java.net.URLEncoder;
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
        System.out.println("🔥🔥🔥 [IPN] RECEIVED FROM VNPAY");

        Map<String, String[]> paramMap = request.getParameterMap();
        Map<String, String> vnpParams = new HashMap<>();

        // In toàn bộ dữ liệu nhận được từ VNPAY
        System.out.println("📦 Raw Params:");
        for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
            System.out.printf("➤ %s = %s%n", entry.getKey(), Arrays.toString(entry.getValue()));
            vnpParams.put(entry.getKey(), entry.getValue()[0]); // không decode ở đây
        }

        // Lấy secure hash và loại bỏ các field không cần thiết cho hash
        String receivedHash = vnpParams.get("vnp_SecureHash");
        vnpParams.remove("vnp_SecureHash");
        vnpParams.remove("vnp_SecureHashType");

        // Sắp xếp key và build rawData
        List<String> sortedKeys = new ArrayList<>(vnpParams.keySet());
        Collections.sort(sortedKeys);

        StringBuilder hashData = new StringBuilder();
        for (int i = 0; i < sortedKeys.size(); i++) {
            String key = sortedKeys.get(i);
            String value = vnpParams.get(key);
            if (value != null && !value.isEmpty()) {
                hashData.append(URLEncoder.encode(key, StandardCharsets.US_ASCII));
                hashData.append("=");
                hashData.append(URLEncoder.encode(value, StandardCharsets.US_ASCII));
                if (i != sortedKeys.size() - 1) {
                    hashData.append("&");
                }
            }
        }

        // In chuỗi rawData để hash
        String rawData = hashData.toString();
        System.out.println("🔐 RawData to hash:");
        System.out.println(rawData);

        // Tính hash
        String calculatedHash = HMACUtil.hmacSHA512(VnPayConfig.vnp_HashSecret, rawData);

        System.out.println("🔑 Calculated Hash : " + calculatedHash);
        System.out.println("🔑 Received Hash   : " + receivedHash);

        // So sánh
        if (!calculatedHash.equals(receivedHash)) {
            System.out.println("❌ Checksum không khớp!");
            return ResponseEntity.ok(Map.of("RspCode", "97", "Message", "Invalid Checksum"));
        }

        // Lấy thông tin giao dịch
        String txnRef = vnpParams.get("vnp_TxnRef");
        String amount = vnpParams.get("vnp_Amount");
        String status = vnpParams.get("vnp_TransactionStatus");
        String responseCode = vnpParams.get("vnp_ResponseCode");

        System.out.println("📌 txnRef: " + txnRef);
        System.out.println("📌 amount: " + amount);
        System.out.println("📌 responseCode: " + responseCode);
        System.out.println("📌 transactionStatus: " + status);

        if ("00".equals(responseCode) && "00".equals(status)) {
            System.out.println("✅ Giao dịch xác nhận THÀNH CÔNG");
            // TODO: cập nhật DB
            return ResponseEntity.ok(Map.of("RspCode", "00", "Message", "Confirm Success"));
        } else {
            System.out.println("❌ Giao dịch THẤT BẠI");
            // TODO: cập nhật DB thất bại
            return ResponseEntity.ok(Map.of("RspCode", "00", "Message", "Confirm Failed Payment"));
        }
    }



}
