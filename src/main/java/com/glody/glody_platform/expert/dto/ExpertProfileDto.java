package com.glody.glody_platform.expert.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Thông tin chuyên gia")
public class ExpertProfileDto {

    @Schema(description = "ID của người dùng (expert)", example = "2")
    private Long userId;

    @Schema(description = "Họ và tên chuyên gia", example = "Bob Chuyên Gia Đa tài")
    private String fullName;

    @Schema(description = "Email của chuyên gia", example = "Bob@EliteExpert.com")
    private String email;

    @Schema(description = "URL ảnh đại diện", example = "https://…/avatar.jpg")
    private String avatarUrl;

    @Schema(description = "Giới thiệu ngắn", example = "Tư vấn viên hàng đầu USA")
    private String bio;

    @Schema(description = "Lĩnh vực chuyên môn", example = "Mỹ, Anh")
    private String expertise;

    @Schema(description = "Chi tiết kinh nghiệm", example = "Xin VISA, Học bổng")
    private String experience;

    @Schema(description = "Số năm kinh nghiệm", example = "8")
    private Integer yearsOfExperience;

//    @Schema(description = "Danh sách tên quốc gia tư vấn", example = "[\"USA\", \"Canada\"]")
//    private List<String> countryNames;
}
