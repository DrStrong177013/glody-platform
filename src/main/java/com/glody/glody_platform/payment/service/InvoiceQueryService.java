package com.glody.glody_platform.payment.service;

import com.glody.glody_platform.payment.dto.InvoiceResponseDto;
import com.glody.glody_platform.payment.mapper.InvoiceMapper;
import com.glody.glody_platform.payment.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceQueryService {
    private final InvoiceRepository invoiceRepository;

    public List<InvoiceResponseDto> getMyInvoices(Long userId) {
        return invoiceRepository.findAllByUserIdAndIsDeletedFalseOrderByCreatedAtDesc(userId)
                .stream()
                .map(InvoiceMapper::toDto)
                .toList();
    }
}