package com.glody.glody_platform.catalog.entity;

import com.glody.glody_platform.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
    private String major; // 🎯 dùng cho matching theo ngành học

    private String degreeType; // BSc, MSc, MBA...
}
