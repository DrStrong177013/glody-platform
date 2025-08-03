package com.glody.glody_platform.payment.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InvoiceResponseDto {
    private Long          id;
    private String        code;
    private Long          packageId;
    private String        note;
    private Double        totalAmount;
    private String        status;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;
    private String        checkoutUrl;  // ← Thêm trường này
}



