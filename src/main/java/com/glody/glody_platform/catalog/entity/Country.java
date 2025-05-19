package com.glody.glody_platform.catalog.entity;

import com.glody.glody_platform.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "countries")
@Getter
@Setter
public class Country extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String name;
}
