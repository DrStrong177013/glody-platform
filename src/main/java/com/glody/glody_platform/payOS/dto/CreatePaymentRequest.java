package com.glody.glody_platform.payOS.dto;

import lombok.Data;

@Data
public class CreatePaymentRequest {
    private Long   orderCode;
    private Long   amount;
    private String description;
    private String returnUrl;
    private String cancelUrl;
    private String signature;
}
