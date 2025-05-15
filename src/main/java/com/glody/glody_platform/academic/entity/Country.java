package com.glody.glody_platform.academic.entity;

import com.glody.glody_platform.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Country extends BaseEntity {
    private String name;
    private String code;

    @OneToMany(mappedBy = "country")
    private List<University> universities;
}