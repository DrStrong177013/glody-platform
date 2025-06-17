package com.glody.glody_platform.payment.service;

import com.glody.glody_platform.config.VnPayConfig;
import com.glody.glody_platform.payment.utils.HMACUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class VnPayService {

    public String createPaymentUrl(HttpServletRequest req, int amount, String orderInfo, String txnRef) throws UnsupportedEncodingException {
        Map<String, String> vnpParams = new HashMap<>();
        vnpParams.put("vnp_Version", "2.1.0");
        vnpParams.put("vnp_Command", "pay");
        vnpParams.put("vnp_TmnCode", VnPayConfig.vnp_TmnCode);
        vnpParams.put("vnp_Amount", String.valueOf(amount * 100));
        vnpParams.put("vnp_CurrCode", "VND");
        vnpParams.put("vnp_TxnRef", txnRef);
        vnpParams.put("vnp_OrderInfo", orderInfo);
        vnpParams.put("vnp_OrderType", "other"); // ✅ Rất quan trọng – bắt buộc

        vnpParams.put("vnp_Locale", "vn");
        vnpParams.put("vnp_ReturnUrl", VnPayConfig.vnp_ReturnUrl);

        // IP xử lý lại tránh IPv6
        String ipAddr = req.getRemoteAddr();
        if ("0:0:0:0:0:0:0:1".equals(ipAddr)) ipAddr = "127.0.0.1";
        vnpParams.put("vnp_IpAddr", ipAddr);

        // Tạo thời gian giao dịch + thời gian hết hạn
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String createDate = formatter.format(cal.getTime());
        cal.add(Calendar.MINUTE, 15); // hết hạn sau 15 phút
        String expireDate = formatter.format(cal.getTime());

        vnpParams.put("vnp_CreateDate", createDate);
        vnpParams.put("vnp_ExpireDate", expireDate);

        // Build dữ liệu để ký
        List<String> fieldNames = new ArrayList<>(vnpParams.keySet());
        Collections.sort(fieldNames);

        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();

        for (String fieldName : fieldNames) {
            String value = vnpParams.get(fieldName);
            if (value != null && !value.isEmpty()) {
                // ❗ hashData giữ nguyên (không encode)
                hashData.append(fieldName).append('=').append(value).append('&');

                // ✅ query encode đúng UTF-8
                query.append(URLEncoder.encode(fieldName, StandardCharsets.UTF_8));
                query.append('=');
                query.append(URLEncoder.encode(value, StandardCharsets.UTF_8));
                query.append('&');
            }
        }

        // Xoá dấu & cuối
        if (hashData.length() > 0) hashData.setLength(hashData.length() - 1);
        if (query.length() > 0) query.setLength(query.length() - 1);

        // Tính hash và tạo full URL
        String secureHash = HMACUtil.hmacSHA512(VnPayConfig.vnp_HashSecret.trim(), hashData.toString());
        query.append("&vnp_SecureHash=").append(secureHash);

        String fullUrl = VnPayConfig.vnp_PayUrl + "?" + query;

        // Debug log
        System.out.println("👉 Full URL send to VNPay: " + fullUrl);
        System.out.println("👉 RawData for hashing: " + hashData);
        System.out.println("👉 SecureHash generated: " + secureHash);

        return fullUrl;
    }
}
