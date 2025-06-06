package com.glody.glody_platform.university.entity;

import com.glody.glody_platform.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "schools")
@Getter
@Setter
public class School extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(name = "name_en")
    private String nameEn;

    private String logoUrl;

    private String location;

    private String website;

    @Column(name = "rank_text")
    private String rankText;


    @Column(columnDefinition = "TEXT")
    private String introduction;

    private Integer establishedYear;

    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Program> programs;

    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Scholarship> scholarships;
}
