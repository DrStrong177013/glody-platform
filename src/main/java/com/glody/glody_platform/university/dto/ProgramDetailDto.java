package com.glody.glody_platform.university.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class ProgramDetailDto {

    private Long id;

    @Schema(description = "Bậc học", example = "THAC_SI")
    private String level;

    @Schema(description = "Ngôn ngữ giảng dạy", example = "ENGLISH")
    private String language;

    @Schema(description = "Ngành học", example = "[\"Khoa học máy tính\", \"Kỹ thuật điện\"]")
    private List<String> majors;

    @Schema(description = "Học phí", example = "36.870 TWD")
    private String tuitionFee;

    @Schema(description = "Chi phí sinh hoạt", example = "20.000 TWD")
    private String livingCost;

    @Schema(description = "Phí ký túc xá", example = "5.000 TWD")
    private String dormFee;

    @Schema(description = "Hỗ trợ học bổng", example = "true")
    private Boolean scholarshipSupport;

    @Schema(description = "Điều kiện GPA", example = "3.2/10")
    private String gpaRequirement;

    @Schema(description = "Yêu cầu ngoại ngữ", example = "TOEFL 500 hoặc tương đương")
    private String languageRequirement;

    @Schema(description = "Danh sách hồ sơ", example = "[\"Bằng tốt nghiệp\", \"CV\", \"Giấy xác nhận tài chính\"]")
    private List<String> documents;

    @Schema(description = "Hạn nộp hồ sơ", example = "2025-06-30")
    private String deadline;
}
