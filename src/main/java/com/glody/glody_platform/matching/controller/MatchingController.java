package com.glody.glody_platform.matching.controller;

import com.glody.glody_platform.matching.dto.*;
import com.glody.glody_platform.matching.service.MatchingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matching")
@RequiredArgsConstructor
public class MatchingController {

    private final MatchingService matchingService;

    @PostMapping("/session")
    public MatchingSessionDto createSession(@RequestParam Long userId, @RequestBody MatchingSessionDto sessionDto) {
        return matchingService.createMatchingSession(userId, sessionDto);
    }

    @GetMapping("/sessions")
    public List<MatchingSessionDto> getUserSessions(@RequestParam Long userId) {
        return matchingService.getUserSessions(userId);
    }

    @GetMapping("/results")
    public List<MatchingResultDto> getResults(@RequestParam Long sessionId) {
        return matchingService.getResultsBySession(sessionId);
    }

    @GetMapping("/scores")
    public List<MatchingScoreDto> getScores(@RequestParam Long resultId) {
        return matchingService.getScoresByResult(resultId);
    }

    @GetMapping("/status")
    public MatchingStatusLogDto getStatus(@RequestParam Long resultId) {
        return matchingService.getStatusLog(resultId);
    }

    @PostMapping("/status")
    public void updateStatus(@RequestParam Long resultId, @RequestParam String status) {
        matchingService.updateStatus(resultId, status);
    }
}