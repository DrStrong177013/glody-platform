package com.glody.glody_platform.payment.repository;

import com.glody.glody_platform.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query("SELECT p FROM Payment p WHERE p.invoice.user.id = :userId AND p.invoice.isDeleted = false ORDER BY p.paidAt DESC")
    List<Payment> findAllByUserId(@Param("userId") Long userId);
    Optional<Payment> findByInvoiceId(Long invoiceId);
    Optional<Payment> findByTransactionId(String transactionId);

}