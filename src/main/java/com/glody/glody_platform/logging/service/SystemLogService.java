package com.glody.glody_platform.logging.service;

import com.glody.glody_platform.logging.dto.SystemLogDto;
import com.glody.glody_platform.logging.entity.SystemLog;
import com.glody.glody_platform.logging.repository.SystemLogRepository;
import com.glody.glody_platform.users.entity.User;
import com.glody.glody_platform.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SystemLogService {

    private final SystemLogRepository systemLogRepository;
    private final UserRepository userRepository;

    public SystemLog log(SystemLogDto dto) {
        User user = userRepository.findById(dto.getUserId()).orElseThrow();
        SystemLog log = new SystemLog();
        log.setUser(user);
        log.setAction(dto.getAction());
        log.setMetadata(dto.getMetadata());
        return systemLogRepository.save(log);
    }
}
