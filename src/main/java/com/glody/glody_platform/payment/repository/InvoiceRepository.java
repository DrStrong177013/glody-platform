package com.glody.glody_platform.payment.repository;

import com.glody.glody_platform.payment.entity.Invoice;
import com.glody.glody_platform.payment.enums.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    Optional<Invoice> findByCode(String code);
    List<Invoice> findAllByUserIdAndIsDeletedFalseOrderByCreatedAtDesc(Long userId);
    List<Invoice> findAllByUserId(Long userId);

    List<Invoice> findAllByStatus(InvoiceStatus status);

    boolean existsByCode(String code);

}