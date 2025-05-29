package com.glody.glody_platform.users.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class UserSubscriptionResponseDto {
    private Long id;
    private Long packageId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isActive;
}
