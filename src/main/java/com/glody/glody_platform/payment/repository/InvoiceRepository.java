package com.glody.glody_platform.payment.repository;

import com.glody.glody_platform.payment.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findAllByUserIdOrderByCreatedAtDesc(Long userId);

}