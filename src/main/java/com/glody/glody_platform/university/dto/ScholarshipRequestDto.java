package com.glody.glody_platform.university.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ScholarshipRequestDto {

    @Schema(description = "Tên học bổng", example = "Học bổng chính phủ Đài Loan MOE")
    private String title;

    @Schema(description = "Tên đơn vị tài trợ", example = "Bộ giáo dục Đài Loan")
    private String sponsor;

    @Schema(description = "Mô tả học bổng", example = "Học bổng dành cho sinh viên quốc tế...")
    private String description;

    @Schema(description = "Giá trị học bổng", example = "12.000TWD/tháng")
    private String value;

    @Schema(description = "Hạn đăng ký", example = "2025-06-30")
    private LocalDate applicationDeadline;

    @Schema(description = "Điều kiện", example = "[\"GPA >= 8.0\", \"IELTS >= 6.5\"]")
    private List<String> conditions;

    @Schema(description = "ID trường", example = "1")
    private Long schoolId;
}
