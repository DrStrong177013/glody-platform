package com.glody.glody_platform.university.dto;

import com.glody.glody_platform.university.enums.DegreeLevel;
import com.glody.glody_platform.university.enums.LanguageType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class ProgramSimpleDto {

    private Long id;

    @Schema(description = "Bậc học", example = "THAC_SI")
    private String level;

    @Schema(description = "Ngôn ngữ giảng dạy", example = "ENGLISH")
    private String language;

    @Schema(description = "Danh sách chuyên ngành", example = "[\"Kỹ thuật máy tính\", \"Trí tuệ nhân tạo\"]")
    private List<String> majors;
}
