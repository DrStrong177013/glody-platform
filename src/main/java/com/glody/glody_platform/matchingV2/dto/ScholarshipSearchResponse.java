package com.glody.glody_platform.matchingV2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScholarshipSearchResponse {
    private MatchingSummary summary;
    private List<ScholarshipDto> scholarships;
    private List<String> recommendations; // Gợi ý chung cho toàn bộ kết quả
}