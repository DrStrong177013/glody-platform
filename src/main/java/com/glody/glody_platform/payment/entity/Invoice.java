package com.glody.glody_platform.payment.entity;

import com.glody.glody_platform.common.BaseEntity;
import com.glody.glody_platform.users.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "invoices")
@Getter
@Setter
public class Invoice extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Double totalAmount;

    private String status; // PAID, UNPAID, PENDING

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    private List<InvoiceItem> items;
}