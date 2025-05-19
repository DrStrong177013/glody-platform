package com.glody.glody_platform.matching.entity;

import com.glody.glody_platform.common.BaseEntity;
import com.glody.glody_platform.users.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "matching_status_logs")
@Getter
@Setter
public class MatchingStatusLog extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "result_id", nullable = false)
    private MatchingResult result;

    private String action; // VIEWED, SELECTED, SKIPPED, REVERTED, etc.
}
