package com.glody.glody_platform.payment.dto;


import com.glody.glody_platform.payment.enums.PaymentStatus;
import com.glody.glody_platform.users.entity.User;
import lombok.Data;

import java.time.LocalDateTime;

// PaymentResponseDto.java
@Data
public class PaymentResponseDto {
    private Long id;
    private User user;
    private String invoiceCode;
    private String provider;
    private String transactionId;
    private String bankCode;
    private String cardType;
    private String status;
    private LocalDateTime paidAt;
}
