package com.glody.glody_platform.users.entity;

import com.glody.glody_platform.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_profiles")
@Getter
@Setter
public class UserProfile extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    private String nationality;
    private String educationLevel;
    private String major;
    private String targetCountry;
    private Integer targetYear;
    private Double gpa;
    private String languageCertificate;
}