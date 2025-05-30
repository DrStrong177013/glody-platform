package com.glody.glody_platform.users.entity;

import com.glody.glody_platform.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity lưu trữ thông tin chứng chỉ ngôn ngữ của người dùng.
 */
@Entity
@Table(name = "language_certificates")
@Getter
@Setter
public class LanguageCertificate extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    private UserProfile profile;

    @Column(nullable = false)
    private String certificateName;   // Ex: TOEIC, IELTS, TOCFL

    @Column(nullable = false)
    private String skill;             // Ex: Listening, Reading, Speaking

    private Integer score;            // Ex: 800, 6.5
    private String resultLevel;       // Ex: A2, B1, C1
}
