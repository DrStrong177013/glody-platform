package com.glody.glody_platform.users.dto;

import com.glody.glody_platform.users.entity.LanguageCertificate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO đại diện cho chứng chỉ ngôn ngữ của người dùng.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LanguageCertificateDto {
    private Long id;

    @Schema(description = "Tên chứng chỉ", example = "IELTS")
    @NotBlank(message = "Tên chứng chỉ không được để trống")
    private String certificateName;

    @Schema(description = "Kỹ năng liên quan", example = "null")
    @NotBlank(message = "Kỹ năng không được để trống")
    private String skill;

    @Schema(description = "Điểm số", example = "6.5")
    @Min(value = 0, message = "Điểm số không được âm")
    @Max(value = 1000, message = "Điểm số tối đa là 1000")
    private Double score;

    @Schema(description = "Trình độ kết quả (nếu có)", example = "null")
    private String resultLevel;

    public LanguageCertificateDto(LanguageCertificate entity) {
        this.certificateName = entity.getCertificateName();
        this.score = entity.getScore();
    }
}

