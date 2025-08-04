package com.glody.glody_platform.payment.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreatePaymentRequest {
    private Long   orderCode;
    private int   amount;
    private String description;
    private List<ItemData> items;
    private String returnUrl;
    private String cancelUrl;
//    private String signature;
}
