package com.glody.glody_platform.university.entity;

import com.glody.glody_platform.common.BaseEntity;
import com.glody.glody_platform.university.enums.DegreeLevel;
import com.glody.glody_platform.university.enums.LanguageType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "programs")
@Getter
@Setter
public class Program extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School school;

    @Enumerated(EnumType.STRING)
    private DegreeLevel level;

    @Enumerated(EnumType.STRING)
    private LanguageType language; // ENGLISH, CHINESE

    @ElementCollection
    @CollectionTable(name = "program_majors", joinColumns = @JoinColumn(name = "program_id"))
    @Column(name = "major")
    private List<String> majors;

    private String tuitionFee;
    private String livingCost;
    private String dormFee;

    private Boolean scholarshipSupport;

    @OneToOne(mappedBy = "program", cascade = CascadeType.ALL, orphanRemoval = true)
    private ProgramRequirement requirement;
}
