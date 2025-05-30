package com.glody.glody_platform.matching.controller;

import com.glody.glody_platform.matching.entity.MatchingHistory;
import com.glody.glody_platform.matching.repository.MatchingHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/matching-history")
@RequiredArgsConstructor
public class MatchingHistoryController {

    private final MatchingHistoryRepository repository;

    @GetMapping
    public ResponseEntity<List<MatchingHistory>> getHistory(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "SCHOLARSHIP") String matchType
    ) {
        return ResponseEntity.ok(repository.findByUserIdAndMatchTypeOrderByCreatedAtDesc(userId, matchType));
    }
}
