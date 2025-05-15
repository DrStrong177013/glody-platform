package com.glody.glody_platform.users.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserSubscriptionDto {
    @NotBlank
    private Long packageId;

    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isActive;
}
