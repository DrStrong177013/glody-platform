package com.glody.glody_platform.logging.repository;

import com.glody.glody_platform.logging.entity.AIModelLog;
import com.glody.glody_platform.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AIModelLogRepository extends JpaRepository<AIModelLog, Long> {
    List<AIModelLog> findByUser(User user);
}
