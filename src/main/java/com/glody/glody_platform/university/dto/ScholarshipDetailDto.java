package com.glody.glody_platform.university.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ScholarshipDetailDto {

    private Long id;

    @Schema(description = "Tên học bổng", example = "Học bổng chính phủ Đài Loan MOE")
    private String title;

    @Schema(description = "Tổ chức cấp học bổng", example = "Bộ giáo dục Đài Loan")
    private String sponsor;

    @Schema(description = "Mô tả học bổng")
    private String description;

    @Schema(description = "Giá trị học bổng", example = "40.000 TWD/ tháng")
    private String value;

    @Schema(description = "Hạn nộp hồ sơ", example = "2025-06-30")
    private LocalDate applicationDeadline;

    @Schema(description = "Danh sách điều kiện", example = "[\"Không phải công dân TQ\", \"Tốt nghiệp THPT\"]")
    private List<String> conditions;

    @Schema(description = "Tên trường (nếu có)", example = "Taiwan Tech")
    private String schoolName;
}
