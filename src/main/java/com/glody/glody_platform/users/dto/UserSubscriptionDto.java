package com.glody.glody_platform.users.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserSubscriptionDto {
    private Long id;
    private Long packageId;
    private Boolean isActive;

    private LocalDate startDate;
    private LocalDate endDate;
}
