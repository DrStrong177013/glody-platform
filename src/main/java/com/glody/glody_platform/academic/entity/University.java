package com.glody.glody_platform.academic.entity;

import com.glody.glody_platform.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Entity
@Getter
@Setter
public class University extends BaseEntity {
    private String name;
    private String website;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @OneToMany(mappedBy = "university")
    private List<Program> programs;

    @OneToMany(mappedBy = "university")
    private List<Scholarship> scholarships;
}