package com.glody.glody_platform.users.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * DTO trả về dữ liệu chứng chỉ ngôn ngữ về client.
 */
@Data
@Builder
public class LanguageCertificateResponse {

    @Schema(description = "ID chứng chỉ", example = "123")
    private Long id;

    @Schema(description = "Tên chứng chỉ", example = "IELTS")
    private String certificateName;

    @Schema(description = "Kỹ năng liên quan", example = "null")
    private String skill;

    @Schema(description = "Điểm số", example = "6.5")
    private Double score;

    @Schema(description = "Trình độ kết quả", example = "null")
    private String resultLevel;
}
