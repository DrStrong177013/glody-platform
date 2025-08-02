package com.glody.glody_platform.payOS.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PayosNotification {
    private String transactionId;
    private String orderId;
    private String status;
    private Long   amount;
    @JsonProperty("checksum")
    private String signature;
}
