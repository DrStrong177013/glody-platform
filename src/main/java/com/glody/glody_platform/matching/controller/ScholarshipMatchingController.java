package com.glody.glody_platform.matching.controller;

import com.glody.glody_platform.matching.dto.ScholarshipMatchDto;
import com.glody.glody_platform.matching.service.ScholarshipMatchingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/scholarships")
@RequiredArgsConstructor
@Tag(name = "Scholarship Matching", description = "Gợi ý học bổng phù hợp với hồ sơ người dùng")
public class ScholarshipMatchingController {

    private final ScholarshipMatchingService matchingService;

    @Operation(summary = "Gợi ý học bổng theo hồ sơ người dùng")
    @GetMapping("/matching")
    public ResponseEntity<List<ScholarshipMatchDto>> suggest(@RequestParam Long userId) {
        return ResponseEntity.ok(matchingService.suggestScholarships(userId));
    }
}
