package com.glody.glody_platform.matchingV2.criteria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScholarshipSearchCriteria {
    private Long userId;
    private String targetCountry;
    private String major;
    private Double gpa;
    private String languageCertificate;
    private Double languageScore;
}