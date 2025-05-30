package com.glody.glody_platform.university.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SchoolSimpleDto {
    private Long id;

    @Schema(description = "Tên trường", example = "Taiwan Tech")
    private String name;

    @Schema(description = "Logo", example = "https://example.com/twtech.png")
    private String logoUrl;
}
