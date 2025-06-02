package com.glody.glody_platform.matching.controller;

import com.glody.glody_platform.matching.dto.ProgramMatchDto;
import com.glody.glody_platform.matching.service.ProgramMatchingService;
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
@RequestMapping("/api/programs")
@RequiredArgsConstructor
@Tag(name = "Program Matching", description = "API gợi ý chương trình học phù hợp")
public class ProgramMatchingController {

    private final ProgramMatchingService matchingService;

    @GetMapping("/matching")
    @Operation(summary = "Gợi ý chương trình học từ hồ sơ người dùng")
    public ResponseEntity<List<ProgramMatchDto>> matchPrograms(@RequestParam Long userId) {
        return ResponseEntity.ok(matchingService.findSuitablePrograms(userId));
    }
}
