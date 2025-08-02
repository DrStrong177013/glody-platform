package com.glody.glody_platform.payOS.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "payment_transaction")
public class PaymentTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderId;
    private Long amount;      // số tiền (đồng)
    private String currency;  // VND, USD,...
    private String status;    // INIT, PENDING, SUCCESS, FAILED

    private Instant createdAt;
    private Instant updatedAt;

}
