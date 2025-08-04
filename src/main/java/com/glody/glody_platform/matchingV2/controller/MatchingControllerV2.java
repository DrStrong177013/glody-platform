package com.glody.glody_platform.matchingV2.controller;

import com.glody.glody_platform.common.ErrorResponse;
import com.glody.glody_platform.matchingV2.criteria.ProgramSearchCriteria;
import com.glody.glody_platform.matchingV2.criteria.ScholarshipSearchCriteria;
import com.glody.glody_platform.matchingV2.dto.*;
import com.glody.glody_platform.matchingV2.service.*;
import com.glody.glody_platform.users.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matching/v2")
@Tag(name = "Matching Controller V2", description = "Tìm các chương trình, trường học, học bổng phù hợp")
@RequiredArgsConstructor
public class MatchingControllerV2 {

    private final ProgramSearchService programSearchService;
    private final ScholarshipMatchingService scholarshipMatchingService;

    @Operation(summary = "Tìm chương trình phù hợp",
            description = "Trả về danh sách chương trình phù hợp với hồ sơ, chỉ gồm thông tin cơ bản như id, tên học bổng, trường, giá trị học bổng, tỉ lệ phù hợp."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(
                    responseCode = "400", description = "Bad Request",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @GetMapping("/programs/light")
    public List<ProgramDto> matchPrograms(
            @ParameterObject @ModelAttribute ProgramSearchCriteria criteria,
            @AuthenticationPrincipal User currentUser
    ) {
        Long userId = currentUser.getId();
        return programSearchService.searchAll(userId, criteria);
    }

    @Operation(
            summary = "Tìm kiếm học bổng phù hợp",
            description = "Trả về danh sách học bổng phù hợp với hồ sơ, chỉ gồm thông tin cơ bản như id, tên học bổng, trường, giá trị học bổng, tỉ lệ phù hợp."
    )
    @GetMapping("/scholarships/light")
    public ResponseEntity<List<ScholarshipLightDto>> matchScholarshipsLight(
            @Parameter(hidden = true) @AuthenticationPrincipal User currentUser
    ) {
        Long userId = currentUser.getId();
        ScholarshipSearchCriteria criteria = new ScholarshipSearchCriteria();
        criteria.setUserId(userId);

        List<ScholarshipLightDto> result = scholarshipMatchingService.matchScholarshipsLight(criteria);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Tìm chương trình phù hợp (đầy đủ)",
            description = "Trả về danh sách chi tiết các chương trình phù hợp với hồ sơ và tiêu chí lọc của người dùng, kèm lý do và tỷ lệ từng tiêu chí."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(
                    responseCode = "400", description = "Bad Request",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @GetMapping("/programs/extended")
    public ProgramSearchResponse matchProgramsExtend(
            @ParameterObject @ModelAttribute ProgramSearchCriteria criteria,
            @AuthenticationPrincipal User currentUser
    ) {
        Long userId = currentUser.getId();
        return programSearchService.searchExtended(userId, criteria);
    }

    @Operation(
            summary = "Tìm kiếm học bổng phù hợp (đầy đủ)",
            description = "Trả về danh sách chi tiết các học bổng phù hợp với hồ sơ và tiêu chí lọc của người dùng, kèm lý do và tỷ lệ từng tiêu chí."
    )
    @GetMapping("/schoolarships/extended")
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

        ScholarshipSearchResponse response = scholarshipMatchingService.matchScholarships(criteria);
        return ResponseEntity.ok(response);
    }

    // Nếu muốn gộp matching school hoặc API khác, bổ sung tại đây

}
