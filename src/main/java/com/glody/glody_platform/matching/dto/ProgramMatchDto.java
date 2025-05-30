package com.glody.glody_platform.matching.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class ProgramMatchDto {

    @Schema(description = "ID chương trình", example = "42")
    private Long id;

    @Schema(description = "Tên trường", example = "Đại học Quốc gia Đài Loan")
    private String schoolName;

    @Schema(description = "Logo trường", example = "https://example.com/logo.png")
    private String schoolLogoUrl;

    @Schema(description = "Bậc học", example = "THAC_SI")
    private String level;

    @Schema(description = "Ngôn ngữ giảng dạy", example = "ENGLISH")
    private String language;

    @Schema(description = "Danh sách ngành học", example = "[\"Khoa học máy tính\", \"Kỹ thuật phần mềm\"]")
    private List<String> majors;

    @Schema(description = "Học phí mỗi năm", example = "36.000 TWD")
    private String tuitionFee;

    @Schema(description = "Chi phí sinh hoạt", example = "20.000 TWD")
    private String livingCost;

    @Schema(description = "Phí ký túc xá", example = "5.000 TWD")
    private String dormFee;

    @Schema(description = "Có hỗ trợ học bổng", example = "true")
    private Boolean scholarshipSupport;

    @Schema(description = "Phần trăm phù hợp với hồ sơ", example = "92")
    private int matchPercentage;
}
