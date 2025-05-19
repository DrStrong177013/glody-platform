package com.glody.glody_platform.logging.repository;


import com.glody.glody_platform.logging.entity.SystemLog;
import com.glody.glody_platform.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SystemLogRepository extends JpaRepository<SystemLog, Long> {
    List<SystemLog> findByUser(User user);
}
