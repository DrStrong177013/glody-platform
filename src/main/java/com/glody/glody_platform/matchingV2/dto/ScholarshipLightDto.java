package com.glody.glody_platform.matchingV2.dto;

import lombok.Data;

@Data
public class ScholarshipLightDto {
    private Long id;
    private String title;
    private String schoolName;
    private Double tuitionFee; // hoặc String value (nếu không lưu tuitionFee riêng)
    private Double matchPercentage;
}
