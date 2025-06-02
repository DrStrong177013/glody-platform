package com.glody.glody_platform.matching.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ScholarshipMatchDto {

    @Schema(description = "ID học bổng", example = "10")
    private Long id;

    @Schema(description = "Tên học bổng", example = "Học bổng chính phủ Đài Loan MOE")
    private String title;

    @Schema(description = "Tên trường cấp học bổng (có thể null nếu học bổng toàn quốc)", example = "Đại học Quốc gia Đài Loan")
    private String schoolName;

    @Schema(description = "URL logo trường", example = "https://example.com/logo.png")
    private String schoolLogoUrl;

    @Schema(description = "Giá trị học bổng", example = "12.000 TWD/tháng")
    private String value;

    @Schema(description = "Hạn nộp đơn", example = "2025-06-30")
    private LocalDate applicationDeadline;

    @Schema(description = "Tóm tắt điều kiện nổi bật", example = "GPA >= 8.0, IELTS >= 6.5")
    private String highlightedConditions;

    @Schema(description = "Phần trăm phù hợp với hồ sơ", example = "85")
    private int matchPercentage;
    @Schema(description = "Nhà tài trợ", example = "Bộ Giáo dục Đài Loan")
    private String sponsor;
    private List<String> matchReasons;

}
