package com.glody.glody_platform.payment.mapper;

import com.glody.glody_platform.payment.dto.PaymentResponseDto;
import com.glody.glody_platform.payment.entity.Payment;

public class PaymentMapper {
    public static PaymentResponseDto toDto(Payment p) {
        PaymentResponseDto dto = new PaymentResponseDto();
        dto.setId(p.getId());
        dto.setInvoiceCode(p.getInvoice().getCode());
        dto.setProvider(p.getProvider());
        dto.setTransactionId(p.getTransactionId());
        dto.setBankCode(p.getBankCode());
        dto.setCardType(p.getCardType());
        dto.setStatus(p.getStatus().name());
        dto.setPaidAt(p.getPaidAt());
        return dto;
    }
}