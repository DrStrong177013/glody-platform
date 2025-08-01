package com.glody.glody_platform.users.mapper;

import com.glody.glody_platform.users.dto.SubscriptionPackageRequestDto;
import com.glody.glody_platform.users.dto.SubscriptionPackageDto;
import com.glody.glody_platform.users.entity.SubscriptionPackage;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionPackageMapper {

    // Entity → Response DTO (có id)
    public SubscriptionPackageDto toDto(SubscriptionPackage e) {
        return new SubscriptionPackageDto(
                e.getId(),
                e.getName(),
                e.getDescription(),
                e.getPrice(),
                e.getDurationDays(),
                e.getFeatures()
        );
    }

    // Request DTO → Entity mới (CREATE)
    public SubscriptionPackage toEntity(SubscriptionPackageRequestDto dto) {
        SubscriptionPackage e = new SubscriptionPackage();
        e.setName(dto.getName());
        e.setDescription(dto.getDescription());
        e.setPrice(dto.getPrice());
        e.setDurationDays(dto.getDurationDays());
        e.setFeatures(dto.getFeatures());
        return e;
    }

    // Request DTO → Entity cũ (UPDATE)
    public void updateFromDto(SubscriptionPackageRequestDto dto, SubscriptionPackage e) {
        e.setName(dto.getName());
        e.setDescription(dto.getDescription());
        e.setPrice(dto.getPrice());
        e.setDurationDays(dto.getDurationDays());
        e.setFeatures(dto.getFeatures());
    }
}
