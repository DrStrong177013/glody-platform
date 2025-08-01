package com.glody.glody_platform.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "ErrorResponse", description = "Error payload")
public class ErrorResponse {
    @Schema(description = "Mô tả lỗi", example = "For input string: \"10 hoặc 3.0\"")
    private String message;
}
