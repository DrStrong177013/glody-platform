package com.glody.glody_platform.catalog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ScholarshipRequirementRequestDto {
    private Long scholarshipId;
    private String requirementType;
    private String requirementCategory;
    private String requirementDetail;
    private Double minValue;
    private Double maxValue;
    private String unit;
}

