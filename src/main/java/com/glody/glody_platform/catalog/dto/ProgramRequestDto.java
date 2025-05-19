package com.glody.glody_platform.catalog.dto;

import lombok.Data;

@Data
public class ProgramRequestDto {
    private String name;
    private String degreeType;
    private Long universityId;
}
