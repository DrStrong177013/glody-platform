package com.glody.glody_platform.catalog.entity;

import com.glody.glody_platform.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "scholarships")
@Getter
@Setter
public class Scholarship extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Double minGpa; // 🎯 dùng cho AI match GPA

    private String applicableMajors; // 🎯 ngành học phù hợp (có thể lưu dạng chuỗi JSON / CSV)
}
