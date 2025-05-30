package com.glody.glody_platform.university.entity;

import com.glody.glody_platform.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "scholarships")
@Getter
@Setter
public class Scholarship extends BaseEntity {


    private String title;

    private String sponsor;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ElementCollection
    @CollectionTable(name = "scholarship_conditions", joinColumns = @JoinColumn(name = "scholarship_id"))
    @Column(name = "condition_text")
    private List<String> conditions;


    private String value;

    private LocalDate applicationDeadline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School school; // Có thể null nếu là học bổng toàn quốc
}
