package com.glody.glody_platform.matching.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchingHistoryDto {
    private Long id;
    private Long userId;
    private Long referenceId;
    private String matchType;
    private int matchPercentage;
    private String additionalInfo;
    private LocalDateTime matchedAt;
}
