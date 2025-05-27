package com.glody.glody_platform.catalog.entity;

import com.glody.glody_platform.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "programs")
@Getter
@Setter
public class Program extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id", nullable = false)
    private University university;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String major; // 🎯 matching AI theo ngành học

    private String degreeType; // BSc, MSc, MBA...

    // OPTIONAL nếu muốn map học bổng cho từng chương trình
    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL)
    private List<ProgramScholarship> programScholarships = new ArrayList<>();
}

