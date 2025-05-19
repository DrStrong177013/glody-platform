package com.glody.glody_platform.expert.dto;

import lombok.Data;

@Data
public class ExpertProfileDto {
    private Long userId;
    private String bio;
    private String expertise;
    private String experience;
}
