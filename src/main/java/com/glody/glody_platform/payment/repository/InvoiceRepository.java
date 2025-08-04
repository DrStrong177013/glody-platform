package com.glody.glody_platform.payment.repository;

import com.glody.glody_platform.payment.entity.Invoice;
import com.glody.glody_platform.payment.enums.InvoiceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    Optional<Invoice> findByCode(String code);
    List<Invoice> findAllByUserIdAndIsDeletedFalseOrderByCreatedAtDesc(Long userId);
    @Query("SELECT i FROM Invoice i WHERE i.status = 'PENDING' AND i.expiredAt <= :now")
    List<Invoice> findExpiredInvoices(@Param("now") LocalDateTime now);
    List<Invoice> findAllByUserId(Long userId);

    List<Invoice> findAllByStatus(InvoiceStatus status);

    boolean existsByCode(String code);
    Page<Invoice> findAllByUserId(Long userId, Pageable pageable);
    Optional<Invoice> findByIdAndUserId(Long id, Long userId);

}