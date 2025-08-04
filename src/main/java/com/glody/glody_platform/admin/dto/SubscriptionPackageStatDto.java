package com.glody.glody_platform.admin.dto;

import lombok.Data;

@Data
public class SubscriptionPackageStatDto {
    private Long packageId;
    private String packageName;
    private Integer totalUser;
    private Integer totalActiveUser;
    private Integer totalExpiredUser;
}