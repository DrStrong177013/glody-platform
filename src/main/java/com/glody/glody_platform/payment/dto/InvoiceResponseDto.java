package com.glody.glody_platform.payment.dto;

import com.glody.glody_platform.payment.enums.InvoiceStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class InvoiceResponseDto {
    private Long id;
    private String code;
    private Long packageId;
    private String packageName;
    private Double totalAmount;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime paidAt;
}


