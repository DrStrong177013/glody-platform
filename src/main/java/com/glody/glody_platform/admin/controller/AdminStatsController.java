package com.glody.glody_platform.admin.controller;

import com.glody.glody_platform.admin.dto.SubscriptionSummaryDto;
import com.glody.glody_platform.users.repository.SubscriptionPackageRepository;
import com.glody.glody_platform.users.repository.UserSubscriptionRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "Admin Stats Controller", description = "Đang sửa")
public class AdminStatsController {

    private final UserSubscriptionRepository userSubscriptionRepository;
    private final SubscriptionPackageRepository subscriptionPackageRepository;

    @GetMapping("/subscription-summary")
    @Operation(
            summary = "Lấy thống kê tổng quan về các gói đăng ký người dùng",
            description = "Bao gồm số gói tổng, đang hoạt động, hết hạn và người dùng miễn phí",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public SubscriptionSummaryDto getSummary() {
        long total = userSubscriptionRepository.countByIsDeletedFalse();
        long active = userSubscriptionRepository.countByIsDeletedFalseAndIsActiveTrue();
        long expired = userSubscriptionRepository.countByIsDeletedFalseAndIsActiveFalse();
        long freeUsers = userSubscriptionRepository.countByIsDeletedFalseAndSubscriptionPackage_Name("FREE");

        SubscriptionSummaryDto dto = new SubscriptionSummaryDto();
        dto.setTotalSubscriptions(total);
        dto.setActiveNow(active);
        dto.setExpired(expired);
        dto.setFreeUsers(freeUsers);
        return dto;
    }
}
