package com.glody.glody_platform.users.controller;

import com.glody.glody_platform.common.PageResponse;
import com.glody.glody_platform.users.entity.SubscriptionPackage;
import com.glody.glody_platform.users.repository.SubscriptionPackageRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller quản lý gói đăng ký (Subscription Packages).
 */
@RestController
@RequestMapping("/api/subscription-packages")
@RequiredArgsConstructor
@Tag(name = "Subscription Package Controller", description = "Quản lý các gói đăng ký và thông tin liên quan")
public class SubscriptionPackageController {

    private final SubscriptionPackageRepository subscriptionPackageRepository;

    /**
     * Lấy danh sách các gói đăng ký (có thể phân trang + sắp xếp).
     */
    @Operation(summary = "Lấy danh sách gói đăng ký (phân trang và sắp xếp)")
    @GetMapping
    public ResponseEntity<PageResponse<SubscriptionPackage>> getAll(
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) Integer page,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        if (size != null) {
            int pageNumber = (page != null) ? page : 0;
            Pageable pageable = PageRequest.of(pageNumber, size, sort);
            Page<SubscriptionPackage> pageResult = subscriptionPackageRepository.findAll(pageable);

            PageResponse.PageInfo pageInfo = new PageResponse.PageInfo(
                    pageResult.getNumber(),
                    pageResult.getSize(),
                    pageResult.getTotalPages(),
                    pageResult.getTotalElements(),
                    pageResult.hasNext(),
                    pageResult.hasPrevious()
            );

            return ResponseEntity.ok(new PageResponse<>(pageResult.getContent(), pageInfo));
        }

        List<SubscriptionPackage> all = subscriptionPackageRepository.findAll(sort);
        PageResponse.PageInfo pageInfo = new PageResponse.PageInfo(
                0,
                all.size(),
                1,
                all.size(),
                false,
                false
        );

        return ResponseEntity.ok(new PageResponse<>(all, pageInfo));
    }

    /**
     * Lấy gói đăng ký theo ID.
     */
    @Operation(summary = "Lấy gói đăng ký theo ID")
    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionPackage> getById(@PathVariable Long id) {
        return subscriptionPackageRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Tạo mới một gói đăng ký.
     */
    @Operation(summary = "Tạo mới gói đăng ký")
    @PostMapping
    public ResponseEntity<SubscriptionPackage> create(@RequestBody SubscriptionPackage pack) {
        SubscriptionPackage created = subscriptionPackageRepository.save(pack);
        return ResponseEntity.ok(created);
    }

    /**
     * Cập nhật một gói đăng ký theo ID.
     */
    @Operation(summary = "Cập nhật thông tin gói đăng ký")
    @PutMapping("/{id}")
    public ResponseEntity<SubscriptionPackage> update(
            @PathVariable Long id,
            @RequestBody SubscriptionPackage updated) {

        return subscriptionPackageRepository.findById(id)
                .map(existing -> {
                    existing.setName(updated.getName());
                    existing.setDescription(updated.getDescription());
                    existing.setPrice(updated.getPrice());
                    existing.setDurationDays(updated.getDurationDays());
                    existing.setFeatures(updated.getFeatures());
                    SubscriptionPackage saved = subscriptionPackageRepository.save(existing);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Xoá một gói đăng ký theo ID.
     */
    @Operation(summary = "Xoá gói đăng ký theo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!subscriptionPackageRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        subscriptionPackageRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
