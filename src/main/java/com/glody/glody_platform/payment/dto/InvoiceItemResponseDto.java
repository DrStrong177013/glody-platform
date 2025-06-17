package com.glody.glody_platform.payment.dto;

import lombok.Data;

@Data
public class InvoiceItemResponseDto {

    private String itemName;

    private Double price;

    private Integer quantity;

    private String description;

    private String referenceType;

    private Long referenceId;
}
