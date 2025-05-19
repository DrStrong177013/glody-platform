package com.glody.glody_platform.matching.dto;

import lombok.Data;

@Data
public class MatchingStatusUpdateRequest {
    private Long userId;
    private Long resultId;
    private String status; // VIEWED, SELECTED, SKIPPED...
}
