package com.glody.glody_platform.matchingV2.dto;

import lombok.Data;

@Data
public class SchoolLightDto {
    private Long id;
    private String name;
    private String country;
    private String logoUrl;
    private Double matchPercentage;
}