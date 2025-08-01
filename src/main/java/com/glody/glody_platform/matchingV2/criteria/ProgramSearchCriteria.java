package com.glody.glody_platform.matchingV2.criteria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramSearchCriteria {
    private Double minGpa = 0.0;
    private String major;
    private String language;        // “IELTS”, “TOEFL”…
    private String targetCountry;   // mã country code
    private Boolean scholarshipSupport;
    private Double gpaScale;
}
