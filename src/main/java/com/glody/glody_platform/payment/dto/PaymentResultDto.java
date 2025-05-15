package com.glody.glody_platform.payment.dto;

import lombok.Data;

@Data
public class PaymentResultDto {
    private String transactionCode;
    private Double amount;
    private String method;
    private String status;
    private Long invoiceId;
}