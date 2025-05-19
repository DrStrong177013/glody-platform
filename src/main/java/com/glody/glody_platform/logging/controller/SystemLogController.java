package com.glody.glody_platform.logging.controller;

import com.glody.glody_platform.logging.dto.SystemLogDto;
import com.glody.glody_platform.logging.entity.SystemLog;
import com.glody.glody_platform.logging.service.SystemLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/system-logs")
@RequiredArgsConstructor
public class SystemLogController {

    private final SystemLogService systemLogService;

    @PostMapping
    public ResponseEntity<SystemLog> log(@RequestBody SystemLogDto dto) {
        return ResponseEntity.ok(systemLogService.log(dto));
    }
}
