package com.glody.glody_platform.matching.controller;

import com.glody.glody_platform.common.PageResponse;
import com.glody.glody_platform.matching.dto.MatchingHistoryDto;
import com.glody.glody_platform.matching.service.MatchingHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matching-history")
@RequiredArgsConstructor
@Tag(name = "Matching History Controller", description = "API lấy lịch sử matching của người dùng")
public class MatchingHistoryController {

    private final MatchingHistoryService service;

    @GetMapping
    @Operation(summary = "Lấy lịch sử matching(program,scholarship) của người dùng (có phân trang, lọc, sắp xếp)")
    public ResponseEntity<PageResponse<MatchingHistoryDto>> getMatchingHistory(
            @RequestParam Long userId,
            @RequestParam String matchType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(service.getMatchingHistory(userId, matchType, page, size));
    }

}
