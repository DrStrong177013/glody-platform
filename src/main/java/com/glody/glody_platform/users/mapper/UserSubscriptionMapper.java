package com.glody.glody_platform.users.mapper;

import com.glody.glody_platform.users.dto.UserSubscriptionDto;
import com.glody.glody_platform.users.dto.UserSubscriptionResponseDto;
import com.glody.glody_platform.users.entity.UserSubscription;

public class UserSubscriptionMapper {


    public static UserSubscriptionResponseDto toDto(UserSubscription sub) {
        return UserSubscriptionResponseDto.builder()
                .id(sub.getId())
                .packageId(sub.getSubscriptionPackage().getId())

                .startDate(sub.getStartDate())
                .endDate(sub.getEndDate())
                .isActive(sub.getIsActive())
                .build();
    }
}
