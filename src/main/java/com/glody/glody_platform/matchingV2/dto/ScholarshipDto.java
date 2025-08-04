package com.glody.glody_platform.matchingV2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScholarshipDto {
    private Long id;
    private String title;
    private String sponsor;
    private String value;
    private String applicationDeadline;
    private String schoolName;
    private Double matchPercentage;
    private Map<String, Integer> criteriaScores; // GPA, LANGUAGE, MAJOR, COUNTRY...
    private List<String> missingRequirements;
    private String detailsUrl; // /scholarships/{id}
    private List<String> recommendations;
}