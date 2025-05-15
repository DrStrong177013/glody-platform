package com.glody.glody_platform.matching.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MatchingStatusLogDto {
    private String status;
    private LocalDateTime updatedAt;
}