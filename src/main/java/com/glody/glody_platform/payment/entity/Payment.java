package com.glody.glody_platform.payment.entity;

import com.glody.glody_platform.common.BaseEntity;
import com.glody.glody_platform.payment.enums.PaymentStatus;
import com.glody.glody_platform.users.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Table(name = "payments")
@Getter
@Setter
public class Payment extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id", nullable = false)
    private Invoice invoice;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // 👈 Thêm dòng này
    private User user;
    private String provider; // e.g., "VNPAY"
    private String transactionId;
    private String bankCode;
    private String cardType;
    private LocalDateTime paidAt;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status; // SUCCESS, FAIL
    private String responseCode;

}
