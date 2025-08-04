// UserInvoiceResponseDto.java
package com.glody.glody_platform.payment.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class UserInvoiceResponseDto {
    private Long id;
    private String code;
    private int totalAmount;
    private String note;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;
    private Long packageId;
    // Không có userId!
}
