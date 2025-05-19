package com.glody.glody_platform.matching.dto;

import lombok.Data;

@Data
public class MatchingResultDto {

    // University Info
    private Long universityId;
    private String universityName;
    private String universityCountry;

    // Program Info
    private Long programId;
    private String programName;
    private String programMajor;
    private String programDegreeType;

    // Scholarship Info (optional)
    private Long scholarshipId;
    private String scholarshipName;

    // Matching scores
    private Double gpaScore;
    private Double majorScore;
    private Double countryScore;
}
