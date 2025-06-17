package com.glody.glody_platform.payment.service;

import com.glody.glody_platform.payment.dto.PaymentResponseDto;
import com.glody.glody_platform.payment.mapper.PaymentMapper;
import com.glody.glody_platform.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentQueryService {
    private final PaymentRepository paymentRepository;

    public List<PaymentResponseDto> getMyPayments(Long userId) {
        return paymentRepository.findAllByUserId(userId)
                .stream()
                .map(PaymentMapper::toDto)
                .toList();
    }
}