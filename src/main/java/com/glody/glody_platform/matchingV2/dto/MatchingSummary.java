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
public class MatchingSummary {
    private int   totalPrograms;
    private int   matchedCount;
    private double averageMatch;
}
