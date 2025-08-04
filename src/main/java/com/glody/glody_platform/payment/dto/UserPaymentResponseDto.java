// UserPaymentResponseDto.java
package com.glody.glody_platform.payment.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserPaymentResponseDto {
    private Long id;
    private String transactionId;
    private String provider;
    private String status;
    private String checkoutUrl;
    private LocalDateTime paidAt;
    private String responseSignature;
    private String bankCode;
    private Long invoiceId;
    // Không có userId!
}
