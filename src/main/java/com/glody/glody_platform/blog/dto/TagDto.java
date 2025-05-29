package com.glody.glody_platform.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class TagDto {

    @Schema(description = "Tên tag duy nhất", example = "Visa")
    private String name;
}
