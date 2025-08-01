package com.glody.glody_platform.matchingV2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * DTO trả về kết quả tìm kiếm chương trình.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "ProgramDto", description = "Thông tin chương trình kèm điểm match")
public class ProgramDto {

    @Schema(description = "ID của chương trình", example = "1")
    private Long id;

    @Schema(description = "Tên chương trình", example = "Computer Science")
    private String title;

    @Schema(description = "Tên trường", example = "Massachusetts Institute of Technology")
    private String schoolName;

    @Schema(description = "Học phí (USD/năm)", example = "50000.0")
    private Double tuitionFee;

    @Schema(description = "Tỉ lệ phù hợp (%)", example = "88.46")
    private Double matchPercentage;

    /**
     * Override setter để tự động làm tròn matchPercentage về 2 chữ số.
     */
    public void setMatchPercentage(Double matchPercentage) {
        this.matchPercentage = BigDecimal
                .valueOf(matchPercentage != null ? matchPercentage : 0)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
