package com.glody.glody_platform.admin.dto;
import lombok.Data;
import java.time.LocalDate;

@Data
public class UserSubscriptionAdminDto {
    private Long id;
    private Long userId;
    private String userName;
    private String email;
    private String phone;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isActive;
    private Long packageId;
    private String packageName;
}