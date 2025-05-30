package com.glody.glody_platform.university.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "DTO để tạo/cập nhật thông tin trường đại học")
@Data
public class SchoolRequestDto {

    @Schema(description = "Tên trường", example = "Đại học Khoa học và Công nghệ Quốc gia Đài Loan")
    private String name;

    @Schema(description = "Tên tiếng Anh", example = "National Taiwan University of Science and Technology")
    private String nameEn;

    @Schema(description = "Địa chỉ logo", example = "https://example.com/logo.png")
    private String logoUrl;

    @Schema(description = "Vị trí", example = "Đài Bắc")
    private String location;

    @Schema(description = "Website", example = "https://www.ntust.edu.tw")
    private String website;

    @Schema(description = "Xếp hạng", example = "2nd tại Đài Loan")
    private String rankText;

    @Schema(description = "Giới thiệu trường", example = "Trường đại học kỹ thuật hàng đầu ở Đài Loan...")
    private String introduction;

    @Schema(description = "Năm thành lập", example = "1974")
    private Integer establishedYear;
}
