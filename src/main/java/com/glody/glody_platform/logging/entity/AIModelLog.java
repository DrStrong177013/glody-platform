package com.glody.glody_platform.logging.entity;

import com.glody.glody_platform.users.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "ai_model_logs")
@Getter
@Setter
public class AIModelLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String modelName;

    @Column(name = "input_prompt" ,columnDefinition = "TEXT")
    private String input;

    @Column(name = "output_text", columnDefinition = "TEXT")
    private String output;

    private LocalDateTime createdAt = LocalDateTime.now();
}
