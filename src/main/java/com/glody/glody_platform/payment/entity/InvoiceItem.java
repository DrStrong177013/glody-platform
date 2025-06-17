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
    @JoinColumn(name = "invoice_id", nullable = false)
    private Invoice invoice;

    private String itemName;
    private Double price;
    private Integer quantity = 1;
    private String description;

    private String referenceType; // e.g., "SUBSCRIPTION_PACKAGE"
    private Long referenceId;
}
