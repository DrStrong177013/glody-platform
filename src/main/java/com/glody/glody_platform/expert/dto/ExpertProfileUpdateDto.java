package com.glody.glody_platform.expert.dto;

import lombok.Data;

import java.util.List;

@Data
public class ExpertProfileUpdateDto {
    private String fullName;
    private String avatarUrl;

    private String bio;
    private String expertise;
    private String experience;
    private Integer yearsOfExperience;

    private List<Long> countryIds; // ID các quốc gia tư vấn
}
