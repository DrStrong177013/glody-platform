package com.glody.glody_platform.expert.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Thông tin liên hệ trong trò chuyện")
public class ChatContactDto {

    @Schema(description = "ID người dùng", example = "5")
    private Long id;

    @Schema(description = "Tên người dùng", example = "Nguyễn Văn A")
    private String fullName;

    @Schema(description = "Link ảnh đại diện", example = "https://cdn.glody.vn/avatar/5.png", nullable = true)
    private String avatarUrl;

    @Schema(description = "Vai trò người dùng", example = "EXPERT")
    private String role;
}
