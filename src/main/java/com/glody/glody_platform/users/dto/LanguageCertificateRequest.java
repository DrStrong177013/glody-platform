package com.glody.glody_platform.users.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import lombok.Data;

/**
 * DTO nhận dữ liệu chứng chỉ ngôn ngữ từ client.
 */
@Data
public class LanguageCertificateRequest {

    @Schema(description = "Tên chứng chỉ", example = "IELTS")
    @NotBlank(message = "Tên chứng chỉ không được để trống")
    private String certificateName;

    @Schema(description = "Kỹ năng liên quan", example = "null")
    private String skill;

    @Schema(description = "Điểm số", example = "6.5")
    @Min(value = 0, message = "Điểm số không được âm")
    @Max(value = 1000, message = "Điểm số tối đa là 1000")
    private Double score;

    @Schema(description = "Trình độ kết quả (nếu có)", example = "null")
    private String resultLevel;
}
