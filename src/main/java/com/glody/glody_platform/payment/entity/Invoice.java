package com.glody.glody_platform.payment.entity;

import com.glody.glody_platform.common.BaseEntity;
import com.glody.glody_platform.payment.enums.InvoiceStatus;

import com.glody.glody_platform.users.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "invoices")
@Getter
@Setter
public class Invoice extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String code; // Mã hóa đơn duy nhất
    private Double totalAmount;

    @Enumerated(EnumType.STRING)
    private InvoiceStatus status = InvoiceStatus.PENDING;

    private LocalDateTime paidAt;
    private String note;
    private LocalDateTime expiredAt;
    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    private List<InvoiceItem> items = new ArrayList<>();

    @OneToOne(mappedBy = "invoice", cascade = CascadeType.ALL)
    private Payment payment;

    @Column(name = "package_id")
    private Long packageId; // Cho biết gói nào được mua (nếu có)
}

