package com.glody.glody_platform.users.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserProfileDto {
    private String fullName;
    private String nationality;
    private String educationLevel;
    private String major;
    private String targetCountry;
    private Integer targetYear;
    private Double gpa;
    private String avatarUrl;

    private List<LanguageCertificateDto> languageCertificates;
}
