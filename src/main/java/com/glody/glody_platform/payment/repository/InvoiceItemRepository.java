package com.glody.glody_platform.payment.repository;

import com.glody.glody_platform.payment.entity.InvoiceItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, Long> {
}