package com.glody.glody_platform.matching.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MatchingSessionDto {
    private Long id;
    private Long userId;
    private LocalDateTime createdAt;
    private List<MatchingResultDto> results;
}