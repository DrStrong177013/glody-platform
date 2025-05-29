package com.glody.glody_platform.users.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserSubscriptionRequestDto {
    @NotNull(message = "Package ID is required")
    private Long packageId;
}
