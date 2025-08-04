package com.glody.glody_platform.matchingV2.controller;

import com.glody.glody_platform.matchingV2.criteria.ScholarshipSearchCriteria;
import com.glody.glody_platform.matchingV2.dto.ScholarshipLightDto;
import com.glody.glody_platform.matchingV2.dto.ScholarshipSearchResponse;
import com.glody.glody_platform.matchingV2.service.ScholarshipMatchingService;
import com.glody.glody_platform.users.entity.User;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Hidden
@RestController
@RequestMapping("/api/scholarships/matching")
@RequiredArgsConstructor
public class ScholarshipMatchingController {

    private final ScholarshipMatchingService matchingService;

    /**
     * API tìm kiếm học bổng phù hợp với hồ sơ người dùng.
     * - Nhận userId từ token đăng nhập (principal).
     * - Nhận thêm các param custom để override profile nếu muốn.
     */
    @GetMapping
    public ResponseEntity<ScholarshipSearchResponse> matchScholarshipsGet(
            @AuthenticationPrincipal User currentUser,
            @RequestParam(required = false) String major,
            @RequestParam(required = false) Double gpa,
            @RequestParam(required = false) String targetCountry

    ) {
        Long userId = currentUser.getId();
        ScholarshipSearchCriteria criteria = new ScholarshipSearchCriteria();
        criteria.setUserId(userId);
        criteria.setMajor(major);
        criteria.setGpa(gpa);
        criteria.setTargetCountry(targetCountry);

        ScholarshipSearchResponse response = matchingService.matchScholarships(criteria);
        return ResponseEntity.ok(response);
    }

    @RestController
    @RequestMapping("/api/scholarships/matching-light")
    @RequiredArgsConstructor
    public class ScholarshipMatchingLightController {
        private final ScholarshipMatchingService matchingService;

        @GetMapping
        public ResponseEntity<List<ScholarshipLightDto>> matchScholarshipsLight(
                @AuthenticationPrincipal(expression = "id") Long userId
        ) {
            ScholarshipSearchCriteria criteria = new ScholarshipSearchCriteria();
            criteria.setUserId(userId);

            List<ScholarshipLightDto> result = matchingService.matchScholarshipsLight(criteria);
            return ResponseEntity.ok(result);
        }
    }

}
