package com.glody.glody_platform.users.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserSubscriptionDto {
    private Long packageId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isActive;
}