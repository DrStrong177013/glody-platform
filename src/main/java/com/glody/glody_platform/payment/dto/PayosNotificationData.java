package com.glody.glody_platform.payment.dto;

import lombok.Data;

@Data
public class PayosNotificationData {
    private Long orderCode;                   // integer
    private Long amount;                      // integer
    private String description;               // string
    private String accountNumber;             // string
    private String reference;                 // string
    private String transactionDateTime;       // string
    private String currency;                  // string
    private String paymentLinkId;             // string
    private String code;                      // string
    private String desc;                      // string
    private String counterAccountBankId;      // string
    private String counterAccountBankName;    // string
    private String counterAccountName;        // string
    private String counterAccountNumber;      // string
    private String virtualAccountName;        // string
    private String virtualAccountNumber;      // string
}
