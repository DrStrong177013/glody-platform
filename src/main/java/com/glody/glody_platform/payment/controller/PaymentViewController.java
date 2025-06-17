package com.glody.glody_platform.payment.controller;

import com.glody.glody_platform.config.VnPayConfig;
import com.glody.glody_platform.payment.entity.Invoice;
import com.glody.glody_platform.payment.entity.Payment;
import com.glody.glody_platform.payment.enums.InvoiceStatus;
import com.glody.glody_platform.payment.enums.PaymentStatus;
import com.glody.glody_platform.payment.repository.InvoiceRepository;
import com.glody.glody_platform.payment.repository.PaymentRepository;
import com.glody.glody_platform.payment.service.PaymentProcessingService;
import com.glody.glody_platform.payment.utils.HMACUtil;
import com.glody.glody_platform.users.dto.UserSubscriptionRequestDto;
import com.glody.glody_platform.users.service.UserSubscriptionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class PaymentViewController {

    private final PaymentProcessingService paymentProcessingService;
    private record ChecksumResult(boolean isValid, String rawData, String calculatedHash) {}

    @GetMapping("/payment-return")
    public String handleReturnUrl(HttpServletRequest request, Model model) throws UnsupportedEncodingException {
        Map<String, String> vnpParams = extractVnpParams(request);
        String txnRef = vnpParams.get("vnp_TxnRef");

        model.addAttribute("txnRef", txnRef);

        String receivedHash = vnpParams.remove("vnp_SecureHash");
        vnpParams.remove("vnp_SecureHashType");

        ChecksumResult result = validateChecksum(vnpParams, receivedHash);

        if (!result.isValid()) {
            model.addAttribute("statusClass", "fail");
            model.addAttribute("message", "⚠️ Chữ ký không hợp lệ!");
            model.addAttribute("rawData", result.rawData());
            model.addAttribute("receivedHash", receivedHash);
            model.addAttribute("calculatedHash", result.calculatedHash());
            return "payment-result";
        }


        try {
            paymentProcessingService.processVnPayReturn(vnpParams);
            model.addAttribute("statusClass", "success");
            model.addAttribute("message", "🎉 Giao dịch thành công và gói đã được kích hoạt!");
        } catch (RuntimeException ex) {
            model.addAttribute("statusClass", "fail");
            model.addAttribute("message", "❌ " + ex.getMessage());
        }

        return "payment-result";
    }

    private ChecksumResult validateChecksum(Map<String, String> params, String receivedHash) {
        List<String> sortedKeys = new ArrayList<>(params.keySet());
        Collections.sort(sortedKeys);

        StringBuilder sb = new StringBuilder();
        for (String key : sortedKeys) {
            sb.append(key).append("=").append(params.get(key)).append("&");
        }
        if (sb.length() > 0) sb.setLength(sb.length() - 1);

        String rawData = sb.toString();
        String calculatedHash = HMACUtil.hmacSHA512(VnPayConfig.vnp_HashSecret, rawData);
        boolean isValid = calculatedHash.equals(receivedHash);

        return new ChecksumResult(isValid, rawData, calculatedHash);
    }




    private Map<String, String> extractVnpParams(HttpServletRequest request) {
        Map<String, String[]> paramMap = request.getParameterMap();
        Map<String, String> result = new HashMap<>();
        for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
            result.put(entry.getKey(), entry.getValue()[0]); // ⚠️ KHÔNG decode
        }
        return result;
    }

}
