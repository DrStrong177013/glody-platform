package com.glody.glody_platform.catalog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProgramRequestDto {

    @NotBlank(message = "Program name is required")
    private String name;

    @NotBlank(message = "Major is required")
    private String major;

    private String degreeType; // Không bắt buộc nhưng nếu muốn thì thêm @NotBlank

    @NotNull(message = "University ID is required")
    private Long universityId;
}
