package com.glody.glody_platform.partner.entity;

import com.glody.glody_platform.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "partners")
@Getter
@Setter
public class Partner extends BaseEntity {

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private PartnerCategory category;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String website;
    private String logoUrl;
}
