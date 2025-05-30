package com.glody.glody_platform.users.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO dùng cho yêu cầu đăng nhập.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequestDto {

    @Schema(description = "Email đăng nhập", example = "user@example.com")
    @Email(message = "Email không hợp lệ")
    @NotBlank(message = "Email không được để trống")
    private String email;

    @Schema(description = "Mật khẩu người dùng", example = "123456")
    @NotBlank(message = "Mật khẩu không được để trống")
    private String password;
}
