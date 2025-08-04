package com.glody.glody_platform.matchingV2.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class SchoolMatchDetailDto {
    private Long id;
    private String name;
    private String country;
    private String logoUrl;
    private Double matchPercentage;
    private Map<String, Integer> criteriaScores;
    private List<String> missingRequirements;
    private List<String> recommendations;
    private List<ProgramDto> matchedPrograms; // nếu muốn show luôn
}