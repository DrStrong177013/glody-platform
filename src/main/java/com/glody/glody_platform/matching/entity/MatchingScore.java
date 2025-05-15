package com.glody.glody_platform.matching.entity;

import com.glody.glody_platform.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class MatchingScore extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "result_id")
    private MatchingResult result;

    private String criteria;
    private Double score;
    private String reason;
}