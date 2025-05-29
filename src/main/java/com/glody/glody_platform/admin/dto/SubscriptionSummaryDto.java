package com.glody.glody_platform.admin.dto;

import lombok.Data;

@Data
public class SubscriptionSummaryDto {
    private long totalSubscriptions;
    private long activeNow;
    private long expired;
    private long freeUsers;
}
