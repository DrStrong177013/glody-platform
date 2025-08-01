package com.glody.glody_platform.users.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionPackageDto {
    private Long id;
    private String name;          // FREE, BASIC, PREMIUM
    private String description;
    private Double price;
    private Integer durationDays; // Số ngày hiệu lực
    private String features;      // JSON hoặc mô tả dạng text
}
