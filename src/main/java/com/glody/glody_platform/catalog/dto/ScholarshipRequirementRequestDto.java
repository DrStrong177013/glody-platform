package com.glody.glody_platform.catalog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScholarshipRequirementRequestDto {

    @NotNull(message = "Scholarship ID is required")
    private Long scholarshipId;

    @NotBlank(message = "Requirement detail is required")
    private String requirementDetail;
}
