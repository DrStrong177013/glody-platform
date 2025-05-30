package com.glody.glody_platform.university.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SchoolResponseDto {

    private Long id;

    @Schema(description = "Tên trường (Tiếng Việt)", example = "Đại học Quốc gia Hà Nội")
    private String name;

    @Schema(description = "Tên trường (Tiếng Anh)", example = "Vietnam National University")
    private String nameEn;

    @Schema(description = "URL logo", example = "https://example.com/logo.png")
    private String logoUrl;

    @Schema(description = "Địa điểm", example = "Hà Nội, Việt Nam")
    private String location;

    @Schema(description = "Website chính thức", example = "https://vnu.edu.vn")
    private String website;

    @Schema(description = "Xếp hạng", example = "Top 500 THE")
    private String rankText;

    @Schema(description = "Giới thiệu về trường", example = "Trường đại học hàng đầu Việt Nam...")
    private String introduction;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Schema(description = "Danh sách chương trình đào tạo")
    private List<ProgramSimpleDto> programs;

    @Schema(description = "Danh sách học bổng")
    private List<ScholarshipSimpleDto> scholarships;
}
