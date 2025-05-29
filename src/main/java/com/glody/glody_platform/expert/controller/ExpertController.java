package com.glody.glody_platform.expert.controller;

import com.glody.glody_platform.common.PageResponse;
import com.glody.glody_platform.expert.dto.ExpertProfileDto;
import com.glody.glody_platform.expert.service.ExpertService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller quản lý tìm kiếm chuyên gia (experts).
 */
@RestController
@RequestMapping("/api/experts")
@RequiredArgsConstructor
@Tag(name = "Expert Controller", description = "Tìm kiếm và quản lý chuyên gia")
public class ExpertController {

    private final ExpertService expertService;

    /**
     * Tìm kiếm chuyên gia theo quốc gia, năm kinh nghiệm, từ khoá, và phân trang.
     *
     * @param country    Quốc gia (viết tắt, ví dụ: "VN")
     * @param minYears   Số năm kinh nghiệm tối thiểu
     * @param keyword    Tên chuyên gia hoặc chuyên môn
     * @param page       Trang hiện tại (bắt đầu từ 0)
     * @param size       Kích thước mỗi trang
     * @param sortBy     Trường sắp xếp (vd: "id", "yearsOfExperience")
     * @param direction  Hướng sắp xếp ("asc" hoặc "desc")
     * @return Kết quả phân trang danh sách chuyên gia
     */
    @Operation(summary = "Tìm kiếm danh sách chuyên gia")
    @GetMapping
    public ResponseEntity<PageResponse<ExpertProfileDto>> searchExperts(
            @RequestParam(required = false) String country,
            @RequestParam(required = false) Integer minYears,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        PageResponse<ExpertProfileDto> result = expertService.searchExperts(
                country, minYears, keyword, page, size, sortBy, direction);
        return ResponseEntity.ok(result);
    }
}
