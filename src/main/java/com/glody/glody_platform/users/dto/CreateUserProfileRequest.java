package com.glody.glody_platform.users.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateUserProfileRequest {
    @NotBlank
    private String nationality;
    private String educationLevel;
    private String major;
    private String targetCountry;
    private Integer targetYear;
    private Double gpa;
    private String languageCertificate;
}