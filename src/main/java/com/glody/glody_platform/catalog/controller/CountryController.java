package com.glody.glody_platform.catalog.controller;

import com.glody.glody_platform.catalog.dto.CountryRequestDto;
import com.glody.glody_platform.catalog.dto.CountryResponseDto;
import com.glody.glody_platform.catalog.entity.Country;
import com.glody.glody_platform.catalog.service.CountryService;
import com.glody.glody_platform.common.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller quản lý danh sách quốc gia (Country).
 */
@RestController
@RequestMapping("/api/countries")
@RequiredArgsConstructor
@Tag(name = "Country Controller", description = "Quản lý danh sách quốc gia")
public class CountryController {

    private final CountryService countryService;

    /**
     * Tìm kiếm quốc gia theo từ khóa và phân trang.
     *
     * @param keyword   Từ khóa tìm kiếm (tên quốc gia)
     * @param size      Kích thước trang
     * @param page      Trang hiện tại
     * @param sortBy    Trường sắp xếp
     * @param direction Hướng sắp xếp (asc/desc)
     * @return Danh sách quốc gia phù hợp
     */
    @Operation(
            summary = "Tìm kiếm quốc gia",
            description = "Tìm kiếm quốc gia theo từ khóa và hỗ trợ phân trang, sắp xếp."
    )
    @GetMapping
    public ResponseEntity<PageResponse<CountryResponseDto>> search(
            @RequestParam(required = false, defaultValue = "") String keyword,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String direction
    ) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        if (size != null) {
            int pageNumber = (page != null) ? page : 0;
            Pageable pageable = PageRequest.of(pageNumber, size, sort);
            Page<CountryResponseDto> paged = countryService.searchPagedDto(keyword, pageable);

            PageResponse.PageInfo pageInfo = new PageResponse.PageInfo(
                    paged.getNumber(),
                    paged.getSize(),
                    paged.getTotalPages(),
                    paged.getTotalElements(),
                    paged.hasNext(),
                    paged.hasPrevious()
            );
            return ResponseEntity.ok(new PageResponse<>(paged.getContent(), pageInfo));
        }

        List<CountryResponseDto> list = countryService.searchAllDto(keyword, sort);
        PageResponse.PageInfo pageInfo = new PageResponse.PageInfo(
                0,
                list.size(),
                1,
                list.size(),
                false,
                false
        );
        return ResponseEntity.ok(new PageResponse<>(list, pageInfo));
    }

    /**
     * Lấy thông tin chi tiết của quốc gia theo ID.
     *
     * @param id ID quốc gia
     * @return Thông tin quốc gia
     */
    @Operation(
            summary = "Lấy thông tin quốc gia theo ID",
            description = "Trả về thông tin chi tiết của một quốc gia dựa trên ID."
    )
    @GetMapping("/{id}")
    public ResponseEntity<CountryResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(countryService.getById(id));
    }

    /**
     * Tạo mới một quốc gia.
     *
     * @param dto Dữ liệu quốc gia
     * @return Thực thể quốc gia sau khi tạo
     */
    @Operation(
            summary = "Tạo quốc gia mới (Admin)",
            description = "Cho phép tạo mới một quốc gia trong hệ thống."
    )
    @PostMapping
    public ResponseEntity<Country> create(@RequestBody CountryRequestDto dto) {
        Country created = countryService.create(dto);
        return ResponseEntity.ok(created);
    }

    /**
     * Cập nhật thông tin một quốc gia.
     *
     * @param id  ID quốc gia
     * @param dto Dữ liệu mới
     * @return Quốc gia sau khi cập nhật
     */
    @Operation(
            summary = "Cập nhật quốc gia (Admin)",
            description = "Cập nhật thông tin cho quốc gia dựa trên ID."
    )
    @PutMapping("/{id}")
    public ResponseEntity<Country> update(@PathVariable Long id, @RequestBody CountryRequestDto dto) {
        Country updated = countryService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    /**
     * Xoá mềm một quốc gia.
     *
     * @param id ID quốc gia
     * @return Thông báo xoá thành công
     */
    @Operation(
            summary = "Xoá mềm quốc gia (Admin)",
            description = "Thực hiện xoá mềm quốc gia khỏi hệ thống (vẫn giữ lại trong database)."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        countryService.softDelete(id);
        return ResponseEntity.ok("🗑️ Quốc gia đã được xoá mềm thành công.");
    }
}
