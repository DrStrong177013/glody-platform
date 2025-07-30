package com.glody.glody_platform.users.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO trả về khi người dùng đăng nhập thành công.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDto {

    @Schema(description = "Access token JWT", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;

    @Schema(description = "Email người dùng", example = "user@example.com")
    private String email;

    @Schema(description = "Tên đầy đủ của người dùng", example = "Người Dùng Thử")
    private String fullName;


}
