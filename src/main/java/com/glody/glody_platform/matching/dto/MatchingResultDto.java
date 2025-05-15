package com.glody.glody_platform.matching.dto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data
public class MatchingResultDto {
    private Long id;
    private Long sessionId;
    private Long scholarshipId;
    private String type;
    private Double totalScore;
    private List<MatchingScoreDto> scores;
    private MatchingStatusLogDto statusLog;
}