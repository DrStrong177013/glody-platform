package com.glody.glody_platform.config;

import org.springframework.stereotype.Component;

@Component
public class VnPayConfig {
    public static final String vnp_Version = "2.1.0";
    public static final String vnp_Command = "pay";
    public static final String vnp_TmnCode = "AMYDSC09"; // lấy từ VNPay
    public static final String vnp_HashSecret = "JA3MQMZH0WDW07A3BPDRQDSMP5YEWUB5"; // lấy từ VNPay
    public static final String vnp_PayUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html"; // đổi khi lên prod
    public static final String vnp_ReturnUrl = "http://localhost:9876/api/payment-return";
}
