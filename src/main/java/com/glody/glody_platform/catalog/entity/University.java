package com.glody.glody_platform.catalog.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.glody.glody_platform.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "universities")
@Getter
@Setter
public class University extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", nullable = false)
    @JsonBackReference // ❌ không serialize lại country → tránh vòng lặp
    private Country country;
}


