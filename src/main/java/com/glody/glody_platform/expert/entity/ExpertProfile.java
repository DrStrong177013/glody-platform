package com.glody.glody_platform.expert.entity;

import com.glody.glody_platform.catalog.entity.Country;
import com.glody.glody_platform.common.BaseEntity;
import com.glody.glody_platform.users.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "expert_profiles")
@Getter
@Setter
public class ExpertProfile extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    private String bio;
    private String expertise;

    private String experience;
    private String avatarUrl;
    @Column(name = "years_of_experience")
    private Integer yearsOfExperience;

    @ManyToMany
    @JoinTable(
            name = "expert_countries",
            joinColumns = @JoinColumn(name = "expert_id"),
            inverseJoinColumns = @JoinColumn(name = "country_id")
    )
    private Set<Country> advisingCountries = new HashSet<>();
}