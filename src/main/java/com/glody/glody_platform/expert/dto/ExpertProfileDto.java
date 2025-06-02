package com.glody.glody_platform.expert.dto;

import lombok.Data;

import java.util.List;

@Data
public class ExpertProfileDto {
    private Long userId;
    private String fullName;
    private String avatarUrl;
    private String email;

    private String bio;
    private String expertise;
    private String experience;
    private Integer yearsOfExperience;

    private List<String> countryNames;
}

