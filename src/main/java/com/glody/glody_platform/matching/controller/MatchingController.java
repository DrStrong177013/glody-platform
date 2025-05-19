package com.glody.glody_platform.matching.controller;

import com.glody.glody_platform.matching.dto.MatchingResultDto;
import com.glody.glody_platform.matching.dto.MatchingStatusUpdateRequest;
import com.glody.glody_platform.matching.entity.MatchingStatus;
import com.glody.glody_platform.matching.entity.MatchingStatusLog;
import com.glody.glody_platform.matching.service.MatchingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matching")
@RequiredArgsConstructor
public class MatchingController {

    private final MatchingService matchingService;

    @PostMapping("/start")
    public List<MatchingResultDto> startMatching(@RequestParam Long userId) {
        return matchingService.matchForUser(userId);
    }

    @PostMapping("/status/update")
    public ResponseEntity<String> updateStatus(@RequestBody MatchingStatusUpdateRequest request) {
        matchingService.updateStatus(request);
        return ResponseEntity.ok("Status updated and log saved");
    }

    @GetMapping("/status")
    public MatchingStatus getCurrentStatus(
            @RequestParam Long userId,
            @RequestParam Long resultId
    ) {
        return matchingService.getCurrentStatus(userId, resultId);
    }

    @GetMapping("/status/log")
    public List<MatchingStatusLog> getLogs(
            @RequestParam Long userId,
            @RequestParam Long resultId
    ) {
        return matchingService.getStatusLogs(userId, resultId);
    }
}
