package com.glody.glody_platform.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CategoryDto {

    @Schema(description = "Tên chuyên mục", example = "Học bổng")
    private String name;

    @Schema(description = "Slug dùng cho URL", example = "hoc-bong")
    private String slug;
}
