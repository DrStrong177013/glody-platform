package com.glody.glody_platform.logging.service;

import com.glody.glody_platform.logging.dto.AIModelLogDto;
import com.glody.glody_platform.logging.entity.AIModelLog;
import com.glody.glody_platform.logging.repository.AIModelLogRepository;
import com.glody.glody_platform.users.entity.User;
import com.glody.glody_platform.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AIModelLogService {

    private final AIModelLogRepository aiModelLogRepository;
    private final UserRepository userRepository;

    public AIModelLog logAI(AIModelLogDto dto) {
        User user = userRepository.findById(dto.getUserId()).orElseThrow();
        AIModelLog log = new AIModelLog();
        log.setUser(user);
        log.setModelName(dto.getModelName());
        log.setInput(dto.getInput());
        log.setOutput(dto.getOutput());
        return aiModelLogRepository.save(log);
    }
}
