package com.glody.glody_platform.expert.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class ExpertProfileUpdateDto {

    @Schema(description = "Họ và tên của chuyên gia tư vấn", example = "Bob Chuyên Gia Đa tài")
    private String fullName;

    @Schema(description = "Đường dẫn URL ảnh đại diện", example = "https://example.com/avatar.jpg")
    private String avatarUrl;

    @Schema(description = "Thông tin giới thiệu ngắn gọn về chuyên gia", example = "Tư vấn viên hàng đầu USA")
    private String bio;

    @Schema(description = "Lĩnh vực hoặc quốc gia chuyên môn", example = "Mỹ, Anh")
    private String expertise;

    @Schema(description = "Chi tiết kinh nghiệm làm việc", example = "Xin VISA, Học bổng")
    private String experience;

    @Schema(description = "Số năm kinh nghiệm tư vấn", example = "8")
    private Integer yearsOfExperience;

//    @Schema(description = "Danh sách ID của các quốc gia tư vấn", example = "[1, 2, 3]")
//    private List<Long> countryIds;
}
