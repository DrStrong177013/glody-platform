package com.glody.glody_platform.expert.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Thông tin cơ bản của user")
public class SimpleUserDto {

    @Schema(description = "ID người dùng", example = "6")
    private Long id;

    @Schema(description = "Họ tên đầy đủ", example = "Nguyễn Văn A")
    private String fullName;

    @Schema(description = "Link ảnh đại diện", example = "https://…/avatar.jpg", nullable = true)
    private String avatarUrl;

    @Schema(description = "Vai trò: STUDENT hoặc EXPERT", example = "EXPERT")
    private String role;
}
