package com.glody.glody_platform.payment.repository;

import com.glody.glody_platform.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findAllByUserIdOrderByCreatedAtDesc(Long userId);

}