package com.glody.glody_platform.matching.entity;

import com.glody.glody_platform.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "matching_scores")
@Getter
@Setter
public class MatchingScore extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "result_id", nullable = false)
    private MatchingResult result;

    private String criterion; // GPA, MAJOR, COUNTRY, etc.

    private Double score;
}
