package com.glody.glody_platform.matchingV2.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "scoring_weights")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoringWeight {
    @Id
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "weight", nullable = false)
    private double weight;
}
