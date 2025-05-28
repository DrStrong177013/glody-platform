package com.glody.glody_platform.catalog.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ScholarshipRequirementResponseDto {
    private Long id;
    private String requirementType;
    private String requirementCategory;
    private String requirementDetail;
    private Double minValue;
    private Double maxValue;
    private String unit;
    private Long scholarshipId;
}

