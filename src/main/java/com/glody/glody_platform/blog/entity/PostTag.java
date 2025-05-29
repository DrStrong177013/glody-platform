package com.glody.glody_platform.blog.entity;

import com.glody.glody_platform.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tags")
@Getter
@Setter
public class PostTag extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String name;
}
