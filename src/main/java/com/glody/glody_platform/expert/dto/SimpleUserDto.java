package com.glody.glody_platform.expert.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Thông tin cơ bản của người dùng trong tin nhắn")
public class SimpleUserDto {

    @Schema(description = "ID người dùng", example = "7")
    private Long id;

    @Schema(description = "Họ tên đầy đủ", example = "Nguyễn Văn A")
    private String fullName;

    @Schema(description = "Link ảnh đại diện", example = "https://cdn.example.com/avatar/7.png", nullable = true)
    private String avatarUrl;

    @Schema(description = "Vai trò: STUDENT hoặc EXPERT", example = "EXPERT")
    private String role;
}
