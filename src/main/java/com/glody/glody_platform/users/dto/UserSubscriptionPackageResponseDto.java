package com.glody.glody_platform.users.dto;

import lombok.Data;

import java.time.LocalDate;
@Data
public class UserSubscriptionPackageResponseDto {
    private Long id;
    private String packageName;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isActive;
    private Boolean isExpired;
    private Double price;
}
