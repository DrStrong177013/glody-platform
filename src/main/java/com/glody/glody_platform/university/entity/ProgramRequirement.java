package com.glody.glody_platform.university.entity;

import com.glody.glody_platform.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "program_requirements")
@Getter
@Setter
public class ProgramRequirement extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "program_id")
    private Program program;

    private String gpaRequirement;

    private String languageRequirement;

    @ElementCollection
    @CollectionTable(name = "program_documents", joinColumns = @JoinColumn(name = "requirement_id"))
    @Column(name = "document_name")
    private List<String> documents;

    private String deadline; // YYYY-MM-DD string
}
