package com.glody.glody_platform.matchingV2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScoringResult {
    private int score; // 0 hoặc 1
    private String reason; // Lý do không match (nếu có)
    private String recommendation; // Gợi ý để đạt
}