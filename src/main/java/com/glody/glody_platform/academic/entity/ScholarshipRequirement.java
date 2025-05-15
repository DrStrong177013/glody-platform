package com.glody.glody_platform.academic.entity;

import com.glody.glody_platform.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class ScholarshipRequirement extends BaseEntity {
    private String criteria;
    private String requirement;

    @ManyToOne
    @JoinColumn(name = "scholarship_id")
    private Scholarship scholarship;
}
