package com.glody.glody_platform.users.controller;

import com.glody.glody_platform.common.PageResponse;
import com.glody.glody_platform.users.dto.SubscriptionPackageDto;
import com.glody.glody_platform.users.dto.SubscriptionPackageRequestDto;
import com.glody.glody_platform.users.entity.SubscriptionPackage;
import com.glody.glody_platform.users.mapper.SubscriptionPackageMapper;
import com.glody.glody_platform.users.repository.SubscriptionPackageRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/subscription-packages")
@RequiredArgsConstructor
@Tag(name = "Subscription Package Controller", description = "Quản lý các gói đăng ký")
public class SubscriptionPackageController {

    private final SubscriptionPackageRepository repo;
    private final SubscriptionPackageMapper mapper;

    @Operation(summary = "Lấy danh sách gói đăng ký")
    @GetMapping
    public ResponseEntity<PageResponse<SubscriptionPackageDto>> getAll(
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) Integer page,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        Sort sort = "desc".equalsIgnoreCase(direction)
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        List<SubscriptionPackageDto> dtos;
        PageResponse.PageInfo pageInfo;

        if (size != null) {
            int p = (page != null ? page : 0);
            Pageable pageable = PageRequest.of(p, size, sort);
            Page<SubscriptionPackage> pg = repo.findAll(pageable);
            dtos = pg.stream().map(mapper::toDto).collect(Collectors.toList());
            pageInfo = new PageResponse.PageInfo(
                    pg.getNumber(), pg.getSize(),
                    pg.getTotalPages(), pg.getTotalElements(),
                    pg.hasNext(), pg.hasPrevious()
            );
        } else {
            List<SubscriptionPackage> all = repo.findAll(sort);
            dtos = all.stream().map(mapper::toDto).collect(Collectors.toList());
            pageInfo = new PageResponse.PageInfo(
                    0, dtos.size(), 1, dtos.size(), false, false
            );
        }

        return ResponseEntity.ok(new PageResponse<>(dtos, pageInfo));
    }

    @Operation(summary = "Lấy gói đăng ký theo ID")
    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionPackageDto> getById(@PathVariable Long id) {
        return repo.findById(id)
                .map(mapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

//    @Operation(summary = "Tạo mới gói đăng ký (Admin)")
//    @PostMapping
//    public ResponseEntity<SubscriptionPackageDto> create(
//            @Valid @RequestBody SubscriptionPackageRequestDto request
//    ) {
//        SubscriptionPackage entity = mapper.toEntity(request);
//        SubscriptionPackage saved = repo.save(entity);
//        return ResponseEntity.ok(mapper.toDto(saved));
//    }

//    @Operation(summary = "Cập nhật thông tin gói đăng ký (Admin)")
//    @PutMapping("/{id}")
//    public ResponseEntity<SubscriptionPackageDto> update(
//            @PathVariable Long id,
//            @Valid @RequestBody SubscriptionPackageRequestDto request
//    ) {
//        return repo.findById(id)
//                .map(existing -> {
//                    mapper.updateFromDto(request, existing);
//                    SubscriptionPackage saved = repo.save(existing);
//                    return ResponseEntity.ok(mapper.toDto(saved));
//                })
//                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy gói đăng ký id=" + id));
//    }

//    @Operation(summary = "Xóa gói đăng ký theo ID (Admin)")
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> delete(@PathVariable Long id) {
//        if (!repo.existsById(id)) {
//            return ResponseEntity.notFound().build();
//        }
//        repo.deleteById(id);
//        return ResponseEntity.noContent().build();
//    }
}
