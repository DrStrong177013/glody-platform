package com.glody.glody_platform.payment.dto;

import lombok.Data;

@Data
public class PayosWebhookRequest {
    private String code;
    private String desc;
    private Boolean success;
    private PayosNotificationData data;
    private String signature;
}
