package com.glody.glody_platform.catalog.dto;

import lombok.*;

@Getter
@Setter
public class UniversityResponseDto {
    private Long id;
    private String name;
    private Long countryId;
    private String countryName;
}