package com.glody.glody_platform.users.entity;

import com.glody.glody_platform.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "user_subscriptions")
@Getter
@Setter
public class UserSubscription extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "package_id", nullable = false)
    private SubscriptionPackage subscriptionPackage;

    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isActive;
}