package com.glody.glody_platform.users.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class RegistrationResponseDto {

    @Schema(description = "ID của người dùng mới", example = "42")
    private Long id;

    @Schema(description = "Email của người dùng", example = "user@example.com")
    private String email;

    @Schema(description = "Họ và tên", example = "Nguyễn Văn A")
    private String fullName;

    @Schema(description = "Danh sách role", example = "[\"STUDENT\"]")
    private List<String> roles;

    @Schema(description = "Tên gói đăng ký mặc định", example = "FREE")
    private String defaultPackageName;

    @Schema(description = "Ngày kết thúc gói mặc định", example = "2025-08-31")
    private LocalDate defaultPackageEndDate;
}
