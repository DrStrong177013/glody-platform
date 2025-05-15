package com.glody.glody_platform.matching.entity;

import com.glody.glody_platform.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class MatchingResult extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "session_id")
    private MatchingSession session;

    private Long scholarshipId;
    private String type; // "SCHOLARSHIP" hoặc "UNIVERSITY"
    private Double totalScore;

    @OneToMany(mappedBy = "result", cascade = CascadeType.ALL)
    private List<MatchingScore> scores;

    @OneToOne(mappedBy = "result", cascade = CascadeType.ALL)
    private MatchingStatusLog statusLog;
}