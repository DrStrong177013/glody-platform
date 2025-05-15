package com.glody.glody_platform.users.entity;

import com.glody.glody_platform.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String roleName; // Example: STUDENT, ADMIN, EXPERT
}