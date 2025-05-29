package com.glody.glody_platform.admin.controller;

import com.glody.glody_platform.admin.dto.SubscriptionSummaryDto;
import com.glody.glody_platform.users.repository.SubscriptionPackageRepository;
import com.glody.glody_platform.users.repository.UserSubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminStatsController {

    private final UserSubscriptionRepository userSubscriptionRepository;
    private final SubscriptionPackageRepository subscriptionPackageRepository;

    @GetMapping("/subscription-summary")
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
