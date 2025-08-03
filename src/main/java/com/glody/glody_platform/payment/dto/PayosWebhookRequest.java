package com.glody.glody_platform.payment.dto;

import lombok.Data;

@Data
public class PayosWebhookRequest {
    private String code;
    private String desc;
    private boolean success;
    private PayosNotificationData data; // Dùng class PayosNotificationData mô phỏng đúng structure trong "data"
    private String signature;
}