package com.glody.glody_platform.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class TagRequestDto {

    @Schema(description = "Tên thẻ gắn", example = "Học Bổng")
    private String name;
}
