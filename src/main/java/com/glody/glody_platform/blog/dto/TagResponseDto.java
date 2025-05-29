package com.glody.glody_platform.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TagResponseDto {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Học Bổng")
    private String name;

    @Schema(example = "2025-05-29T21:00:00")
    private LocalDateTime createdAt;

    @Schema(example = "2025-05-29T21:05:00")
    private LocalDateTime updatedAt;
}
