package com.glody.glody_platform.payment.dto;

import lombok.Data;

@Data
public class PayosNotification {
    private Long   orderCode;  // chính là invoice.id khi tạo link
    private Long   amount;
    private String status;     // "SUCCESS" / "FAILED"
    private String signature;  // checksum HMAC_SHA256
}
