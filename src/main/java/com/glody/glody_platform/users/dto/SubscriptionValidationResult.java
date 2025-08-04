package com.glody.glody_platform.users.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionValidationResult {
    private boolean valid;
    private String message;
}
