package com.glody.glody_platform.catalog.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScholarshipResponseDto {
    private Long id;
    private String name;
    private String description;
    private Double minGpa;
    private String applicableMajors;
    private List<Long> programIds;
}
