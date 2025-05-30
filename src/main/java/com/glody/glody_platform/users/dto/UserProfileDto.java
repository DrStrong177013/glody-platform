package com.glody.glody_platform.users.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO chứa thông tin hồ sơ cá nhân của người dùng.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDto {

    @Schema(description = "Họ tên đầy đủ", example = "Người Dùng Thử")
    @NotBlank(message = "Họ tên không được để trống")
    private String fullName;

    @Schema(description = "Quốc tịch", example = "Vietnam")
    private String nationality;

    @Schema(description = "Trình độ học vấn", example = "Đại học")
    private String educationLevel;

    @Schema(description = "Chuyên ngành đang học", example = "Công nghệ thông tin")
    private String major;

    @Schema(description = "Quốc gia mục tiêu du học", example = "Japan")
    private String targetCountry;

    @Schema(description = "Năm mục tiêu du học", example = "2025")
    @Min(value = 2000)
    @Max(value = 2100)
    private Integer targetYear;

    @Schema(description = "GPA hiện tại", example = "3.75")
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "10.0")
    private Double gpa;

    @Schema(description = "URL ảnh đại diện", example = "https://cdn.glody.vn/avatar.jpg")
    private String avatarUrl;

    @Schema(description = "Danh sách chứng chỉ ngôn ngữ của người dùng")
    private List<LanguageCertificateDto> languageCertificates;
}
