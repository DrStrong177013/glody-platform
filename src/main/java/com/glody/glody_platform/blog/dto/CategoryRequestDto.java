package com.glody.glody_platform.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CategoryRequestDto {

    @Schema(description = "Tên chuyên mục bài viết", example = "Học Bổng")
    private String name;
}
