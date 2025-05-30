package com.glody.glody_platform.users.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * DTO dùng để trả thông tin người dùng (ẩn thông tin nhạy cảm).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    @Schema(description = "ID người dùng", example = "1")
    private Long id;

    @Schema(description = "Địa chỉ email", example = "user@example.com")
    private String email;

    @Schema(description = "Số điện thoại", example = "0901234567")
    private String phone;
}
