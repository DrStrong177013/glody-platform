package com.glody.glody_platform.payment.entity;

import com.glody.glody_platform.common.BaseEntity;
import com.glody.glody_platform.users.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "payments")
@Getter
@Setter
public class Payment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Double amount;

    private String method; // VNPAY, PAYPAL, etc.

    private String status; // SUCCESS, FAILED

    private String transactionCode;

    @Column(columnDefinition = "TEXT")
    private String paymentGatewayResponse;
}