package com.glody.glody_platform.payment.dto;

import lombok.Data;

import java.util.List;

@Data
public class InvoiceRequestDto {

    private Long userId;
    private Long packageId;
    private String note;

    private List<InvoiceItemRequestDto> items;
}

