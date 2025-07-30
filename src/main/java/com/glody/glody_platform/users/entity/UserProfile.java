package com.glody.glody_platform.users.entity;

import com.glody.glody_platform.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_profiles")
@Getter
@Setter
public class UserProfile extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;


    private String nationality;


    private String educationLevel;


    private String major;

    private String targetCountry;
    private Integer targetYear;
    private String targetSemester; // Ví dụ: "Mùa Thu"
    private Double gpa;
    private Double gpaScale;

    private String universityName;

    private String avatarUrl;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LanguageCertificate> languageCertificates = new ArrayList<>();
@Column(name = "second_language_certificate")
    private String secondLanguageCertificate;
@Column(name = "extracurricular_activities")
    private String extracurricularActivities;
}
