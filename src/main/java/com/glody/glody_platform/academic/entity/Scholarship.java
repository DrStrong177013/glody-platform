package com.glody.glody_platform.academic.entity;

import com.glody.glody_platform.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Scholarship extends BaseEntity {
    private String name;
    private String description;
    private Double value;

    @ManyToOne
    @JoinColumn(name = "university_id")
    private University university;

    @OneToMany(mappedBy = "scholarship")
    private List<ScholarshipRequirement> requirements;
}