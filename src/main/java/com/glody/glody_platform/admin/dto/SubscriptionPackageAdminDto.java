package com.glody.glody_platform.admin.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SubscriptionPackageAdminDto {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer durationDays;
    private Integer totalUser;
    private Integer totalActiveUser;
    private Integer totalExpiredUser;
    private LocalDateTime createdAt;
    private Boolean status;
}