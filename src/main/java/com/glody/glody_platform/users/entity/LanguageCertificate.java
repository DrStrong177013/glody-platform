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
    private String certificateName;

    @Column(nullable = false)
    private String skill;

    private Double score;
    private String resultLevel;
}
