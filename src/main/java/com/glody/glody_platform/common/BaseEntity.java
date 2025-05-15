package com.glody.glody_platform.common;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {
    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();

    private Boolean isDeleted = false;

    private LocalDateTime deletedAt;

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}