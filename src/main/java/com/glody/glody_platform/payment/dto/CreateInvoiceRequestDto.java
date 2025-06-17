package com.glody.glody_platform.payment.dto;

import lombok.Data;

@Data
public class CreateInvoiceRequestDto {
    private Long packageId;
    private String note;
}
