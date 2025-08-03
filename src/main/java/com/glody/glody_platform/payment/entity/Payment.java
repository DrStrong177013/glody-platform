package com.glody.glody_platform.payment.entity;

import com.glody.glody_platform.common.BaseEntity;
import com.glody.glody_platform.payment.enums.PaymentStatus;
import com.glody.glody_platform.payment.entity.Invoice;
import com.glody.glody_platform.users.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments",
        indexes = {
                @Index(name="idx_payment_invoice", columnList="invoice_id"),
                @Index(name="idx_payment_user",    columnList="user_id")
        })
@Getter @Setter
public class Payment extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id", nullable = false)
    private Invoice invoice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String provider;

    // ID của link trả về khi tạo PayOS link
    @Column(name="payment_link_id")
    private String paymentLinkId;

    // URL cho frontend redirect
    @Column(name="checkout_url", length=512)
    private String checkoutUrl;

    // Transaction ID sau khi khách thanh toán xong (nếu có)
    @Column(name="transaction_id")
    private String transactionId;

    // (Tuỳ chọn) signature PayOS trả về khi tạo link -> để verify
    @Column(name="response_signature", length=128)
    private String responseSignature;

    @Column(name="bank_code")
    private String bankCode;

    @Column(name="card_type")
    private String cardType;

    @Column(name="paid_at")
    private LocalDateTime paidAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @Column(name="response_code")
    private String responseCode;
}

