package com.glody.glody_platform.payment.dto;

import lombok.Data;

@Data
public class CreateInvoiceRequestDto {
    private Long   packageId;
    private String returnUrl;   // URL PayOS redirect khi thanh toán xong
    private String cancelUrl;   // URL PayOS redirect khi hủy
}