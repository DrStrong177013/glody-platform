package com.glody.glody_platform.matching.entity;

import com.glody.glody_platform.common.BaseEntity;
import com.glody.glody_platform.users.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "matching_histories")
@Getter
@Setter
public class MatchingHistory extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Long referenceId; // ID của Program hoặc Scholarship
    private String matchType; // "SCHOLARSHIP" hoặc "PROGRAM"

    private int matchPercentage;

    private String additionalInfo; // Tùy chọn: tên trường, học bổng,...
}
