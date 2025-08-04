package com.glody.glody_platform.matchingV2.controller;

import com.glody.glody_platform.matchingV2.criteria.ProgramSearchCriteria;
import com.glody.glody_platform.matchingV2.dto.ProgramDto;
import com.glody.glody_platform.matchingV2.dto.ProgramSearchResponse;
import com.glody.glody_platform.matchingV2.service.ProgramSearchService;
import com.glody.glody_platform.common.ErrorResponse;
import com.glody.glody_platform.users.entity.User;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springdoc.core.annotations.ParameterObject;
import java.security.Principal;
import java.util.List;
@Hidden
@RestController
@RequestMapping("/api/search/programs")
@Tag(name = "Matching Controller V2", description = "Tìm các chương trình, trường học, học bổng phù hợp")
public class ProgramSearchController {
    private final ProgramSearchService service;

    public ProgramSearchController(ProgramSearchService service) {
        this.service = service;
    }

    @Operation(summary = "Tìm chương trình phù hợp")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public List<ProgramDto> search(
            @ParameterObject @ModelAttribute ProgramSearchCriteria criteria,
            @AuthenticationPrincipal User currentUser
    ) {
        Long userId = currentUser.getId();
        return service.searchAll(userId, criteria);
    }

    @Operation(summary = "Tìm chương trình phù hợp (PREMIUM)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/extended")
    public ProgramSearchResponse searchExtended(
            @ParameterObject @ModelAttribute ProgramSearchCriteria criteria,
            @AuthenticationPrincipal User currentUser
    ) {
        Long userId = currentUser.getId();
        return service.searchExtended(userId, criteria);
    }
}
