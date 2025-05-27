package com.glody.glody_platform.catalog.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScholarshipRequirementResponseDto {
    private Long id;
    private String requirementDetail;
}
