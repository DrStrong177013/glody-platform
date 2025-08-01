package com.glody.glody_platform.matchingV2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProgramDetailDto {
    private Long   id;
    private String title;
    private String schoolName;
    private Double tuitionFee;
    private Double matchPercentage;
    private Map<String, Double> criteriaScores;      // e.g. {"GPA":0.85, ...}
    private List<String> missingRequirements;        // e.g. ["COUNTRY", "MAJOR"]
    private String applicationDeadline;              // từ ProgramRequirement.deadline
    private String detailsUrl;                       // ví dụ "/programs/{id}"
    private List<String> recommendations;
}
