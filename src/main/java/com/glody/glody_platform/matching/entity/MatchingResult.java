package com.glody.glody_platform.matching.entity;

import com.glody.glody_platform.catalog.entity.Program;
import com.glody.glody_platform.catalog.entity.Scholarship;
import com.glody.glody_platform.catalog.entity.University;
import com.glody.glody_platform.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "matching_results")
@Getter
@Setter
public class MatchingResult extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private MatchingSession session;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id")
    private University university;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id")
    private Program program;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scholarship_id")
    private Scholarship scholarship;
}
