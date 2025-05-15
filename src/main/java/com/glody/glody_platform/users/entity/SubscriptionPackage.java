package com.glody.glody_platform.users.entity;

import com.glody.glody_platform.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "subscription_packages")
@Getter
@Setter
public class SubscriptionPackage extends BaseEntity {

    private String name; // FREE, BASIC, PREMIUM
    private String description;
    private Double price;
    private Integer durationDays; // Số ngày hiệu lực
    private String features; // JSON hoặc mô tả dạng text
}