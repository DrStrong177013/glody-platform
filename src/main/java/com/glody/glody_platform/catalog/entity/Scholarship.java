package com.glody.glody_platform.catalog.entity;

import com.glody.glody_platform.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "scholarships")
@Getter
@Setter
public class Scholarship extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Double minGpa;

    @Column(columnDefinition = "TEXT")
    private String applicableMajors; // 🎯 CSV hoặc JSON string

    @OneToMany(mappedBy = "scholarship", cascade = CascadeType.ALL)
    private List<ScholarshipRequirement> requirements = new ArrayList<>();

    @OneToMany(mappedBy = "scholarship", cascade = CascadeType.ALL)
    private List<ProgramScholarship> programScholarships = new ArrayList<>();
}

