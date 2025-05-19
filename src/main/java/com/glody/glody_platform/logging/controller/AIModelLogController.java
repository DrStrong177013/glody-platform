package com.glody.glody_platform.logging.controller;

import com.glody.glody_platform.logging.dto.AIModelLogDto;
import com.glody.glody_platform.logging.entity.AIModelLog;
import com.glody.glody_platform.logging.service.AIModelLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai-logs")
@RequiredArgsConstructor
public class AIModelLogController {

    private final AIModelLogService aiModelLogService;

    @PostMapping
    public ResponseEntity<AIModelLog> log(@RequestBody AIModelLogDto dto) {
        return ResponseEntity.ok(aiModelLogService.logAI(dto));
    }
}
