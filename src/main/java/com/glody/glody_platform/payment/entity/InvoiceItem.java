package com.glody.glody_platform.payment.entity;
import com.glody.glody_platform.common.BaseEntity;
import com.glody.glody_platform.users.entity.SubscriptionPackage;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "invoice_items")
@Getter
@Setter
public class InvoiceItem extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "package_id")
    private SubscriptionPackage subscriptionPackage;

    private Integer quantity;

    private Double unitPrice;
}