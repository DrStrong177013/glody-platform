package com.glody.glody_platform.university.dto;

import com.glody.glody_platform.university.enums.DegreeLevel;
import com.glody.glody_platform.university.enums.LanguageType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class ProgramRequestDto {
    @Schema(description = "Id trường", example = "1")
    private Long schoolId;

    private DegreeLevel level;
    private LanguageType language;

    private List<String> majors;
    private String tuitionFee;
    private String livingCost;
    private String dormFee;
    private Boolean scholarshipSupport;

    private ProgramRequirementDto requirement;
}
