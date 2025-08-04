// AdminPaymentResponseDto.java
package com.glody.glody_platform.payment.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AdminPaymentResponseDto {
    private Long id;
    private String transactionId;
    private String provider;
    private String status;
    private String checkoutUrl;
    private LocalDateTime paidAt;
    private String responseSignature;
    private String bankCode;
    private Long invoiceId;
    private Long userId; // Thêm userId
}
