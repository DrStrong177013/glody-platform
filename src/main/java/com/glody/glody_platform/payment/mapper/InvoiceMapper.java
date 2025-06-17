package com.glody.glody_platform.payment.mapper;

import com.glody.glody_platform.payment.dto.InvoiceResponseDto;
import com.glody.glody_platform.payment.entity.Invoice;

public class InvoiceMapper {
    public static InvoiceResponseDto toDto(Invoice invoice) {
        InvoiceResponseDto dto = new InvoiceResponseDto();
        dto.setId(invoice.getId());
        dto.setCode(invoice.getCode());
        dto.setPackageId(invoice.getPackageId());
        dto.setTotalAmount(invoice.getTotalAmount());
        dto.setStatus(invoice.getStatus().name());
        dto.setCreatedAt(invoice.getCreatedAt());
        dto.setPaidAt(invoice.getPaidAt());
        dto.setNote(invoice.getNote());
        dto.setExpiredAt(invoice.getExpiredAt());

        return dto;
    }
}