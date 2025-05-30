package com.glody.glody_platform.university.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class ProgramRequirementDto {

    @Schema(description = "Yêu cầu GPA", example = "7.5/10 hoặc 3.0/4")
    private String gpaRequirement;

    @Schema(description = "Yêu cầu tiếng Anh/Tiếng Hoa", example = "IELTS 6.5 hoặc TOEFL 80")
    private String languageRequirement;

    @Schema(description = "Hồ sơ cần nộp", example = "[\"Bằng tốt nghiệp\", \"Sơ yếu lý lịch\", \"Thư giới thiệu\"]")
    private List<String> documents;

    @Schema(description = "Hạn nộp hồ sơ", example = "2025-04-15")
    private String deadline;
}
