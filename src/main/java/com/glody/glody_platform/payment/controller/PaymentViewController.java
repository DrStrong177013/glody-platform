package com.glody.glody_platform.payment.controller;

import com.glody.glody_platform.config.VnPayConfig;
import com.glody.glody_platform.payment.utils.HMACUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class PaymentViewController {

    @GetMapping("/payment-return")
    public String handleReturnUrl(HttpServletRequest request, Model model) throws UnsupportedEncodingException {
        Map<String, String[]> paramMap = request.getParameterMap();
        Map<String, String> vnpParams = new HashMap<>();

        for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
            vnpParams.put(entry.getKey(), URLDecoder.decode(entry.getValue()[0], StandardCharsets.UTF_8));
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

        boolean isValid = calculatedHash.equals(receivedHash);
        boolean isSuccess = "00".equals(vnpParams.get("vnp_ResponseCode"));

        model.addAttribute("txnRef", vnpParams.get("vnp_TxnRef"));
        model.addAttribute("statusClass", isValid && isSuccess ? "success" : "fail");
        model.addAttribute("message", isValid
                ? (isSuccess ? "🎉 Giao dịch thành công!" : "❌ Giao dịch không thành công.")
                : "⚠️ Chữ ký không hợp lệ!");

        return "payment-result"; // HTML trong templates/
    }
}
