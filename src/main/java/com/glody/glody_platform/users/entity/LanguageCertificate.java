package com.glody.glody_platform.users.entity;

import com.glody.glody_platform.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "language_certificates")
@Getter
@Setter
public class LanguageCertificate extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    private UserProfile profile;

    private String certificateName;   // TOEIC, IELTS, TOCFL, v.v.
    private String skill;             // Listening, Reading, Speaking, Writing
    private Integer score;            // 345, 325, etc.
    private String resultLevel;       // A2, B1 (nếu có)
}
