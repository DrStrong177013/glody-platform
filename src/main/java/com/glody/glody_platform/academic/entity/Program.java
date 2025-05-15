package com.glody.glody_platform.academic.entity;

import com.glody.glody_platform.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Entity
@Getter
@Setter
public class Program extends BaseEntity {
    private String name;
    private String level; // bachelor, master, etc.

    @ManyToOne
    @JoinColumn(name = "university_id")
    private University university;
}