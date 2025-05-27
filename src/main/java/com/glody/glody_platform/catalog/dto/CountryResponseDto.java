package com.glody.glody_platform.catalog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountryResponseDto {
    private Long id;
    private String name;
    private String code;
    private List<UniversityDto> universities;
}
