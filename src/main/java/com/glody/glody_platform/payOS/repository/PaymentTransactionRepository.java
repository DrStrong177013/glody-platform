package com.glody.glody_platform.payOS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.glody.glody_platform.payOS.entity.PaymentTransaction;

import java.util.Optional;

@Repository
public interface PaymentTransactionRepository
        extends JpaRepository<PaymentTransaction, Long> {

    // Phương thức tìm giao dịch theo orderId
    Optional<PaymentTransaction> findByOrderId(String orderId);
}
