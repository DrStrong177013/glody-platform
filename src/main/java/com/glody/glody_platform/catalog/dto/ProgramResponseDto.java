package com.glody.glody_platform.catalog.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProgramResponseDto {
    private Long id;
    private String name;
    private String major;
    private String degreeType;
    private Long universityId;
    private String universityName;
}