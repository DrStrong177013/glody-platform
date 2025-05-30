package com.glody.glody_platform.university.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ScholarshipSimpleDto {
    private Long id;

    @Schema(description = "Tên học bổng", example = "Học bổng chính phủ Đài Loan MOE")
    private String title;

    @Schema(description = "Đơn vị tài trợ", example = "Bộ Giáo dục Đài Loan")
    private String sponsor;

    @Schema(description = "Giá trị học bổng", example = "40.000 TWD học phí + 20.000 TWD/tháng sinh hoạt")
    private String value;
}
