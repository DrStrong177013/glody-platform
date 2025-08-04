// AdminInvoiceResponseDto.java
package com.glody.glody_platform.payment.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AdminInvoiceResponseDto {
    private Long id;
    private String code;
    private int totalAmount;
    private String note;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;
    private Long packageId;
    private Long userId; // Thêm userId cho admin
}
