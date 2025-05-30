package com.glody.glody_platform.users.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO đại diện cho dữ liệu đầu vào khi tạo tài khoản người dùng mới.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @Schema(description = "Họ tên đầy đủ", example = "Người Dùng Thử")
    @NotBlank(message = "Họ tên không được để trống")
    private String fullName;

    @Schema(description = "Địa chỉ email hợp lệ", example = "user@example.com")
    @Email(message = "Email không hợp lệ")
    @NotBlank(message = "Email không được để trống")
    private String email;

    @Schema(description = "Mật khẩu người dùng", example = "123456")
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
    private String password;

    @Schema(description = "Số điện thoại (nếu có)", example = "0901234567")
    private String phone;

    @Schema(description = "Người dùng này có phải chuyên gia không?", example = "false")
    private Boolean isExpert = false;
}
