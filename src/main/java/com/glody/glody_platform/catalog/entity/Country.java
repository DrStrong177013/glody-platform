package com.glody.glody_platform.catalog.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.glody.glody_platform.common.BaseEntity;
import com.glody.glody_platform.university.entity.School;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "countries")
@Getter
@Setter
public class Country extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 10)
    private String code;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    @JsonManagedReference // ✅ quản lý chiều serialize chính
    private List<University> universities = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<School> schools = new ArrayList<>();
}


