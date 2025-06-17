package com.glody.glody_platform.payment.service;

import com.glody.glody_platform.config.VnPayConfig;
import com.glody.glody_platform.payment.utils.HMACUtil;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class VnPayService {

    public String createPaymentUrl(HttpServletRequest req, int amount, String orderInfo) throws UnsupportedEncodingException {
        Map<String, String> vnpParams = new HashMap<>();
        vnpParams.put("vnp_Version", VnPayConfig.vnp_Version);
        vnpParams.put("vnp_Command", VnPayConfig.vnp_Command);
        vnpParams.put("vnp_TmnCode", VnPayConfig.vnp_TmnCode);
        vnpParams.put("vnp_Amount", String.valueOf(amount * 100));
        vnpParams.put("vnp_CurrCode", "VND");
        vnpParams.put("vnp_TxnRef", String.valueOf(System.currentTimeMillis()));
        vnpParams.put("vnp_OrderInfo", orderInfo);
        vnpParams.put("vnp_Locale", "vn");
        vnpParams.put("vnp_ReturnUrl", VnPayConfig.vnp_ReturnUrl);
        vnpParams.put("vnp_IpAddr", req.getRemoteAddr());
        vnpParams.put("vnp_CreateDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));

        List<String> fieldNames = new ArrayList<>(vnpParams.keySet());
        Collections.sort(fieldNames);

        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();

        for (String fieldName : fieldNames) {
            String value = vnpParams.get(fieldName);
            if (!StringUtils.isEmpty(value)) {
                hashData.append(fieldName).append('=').append(URLEncoder.encode(value, StandardCharsets.US_ASCII)).append('&');
                query.append(fieldName).append('=').append(URLEncoder.encode(value, StandardCharsets.US_ASCII)).append('&');
            }
        }

        hashData.setLength(hashData.length() - 1);
        query.setLength(query.length() - 1);

        String vnp_SecureHash = HMACUtil.hmacSHA512(VnPayConfig.vnp_HashSecret, hashData.toString());
        query.append("&vnp_SecureHash=").append(vnp_SecureHash);
        System.out.println("👉 Full URL send to VNPay: " + VnPayConfig.vnp_PayUrl + "?" + query);
        return VnPayConfig.vnp_PayUrl + "?" + query.toString();
    }
}
