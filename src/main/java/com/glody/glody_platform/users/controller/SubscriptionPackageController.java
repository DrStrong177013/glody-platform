package com.glody.glody_platform.users.controller;

import com.glody.glody_platform.common.PageResponse;
import com.glody.glody_platform.users.entity.SubscriptionPackage;
import com.glody.glody_platform.users.repository.SubscriptionPackageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscription-packages")
@RequiredArgsConstructor
public class SubscriptionPackageController {

    private final SubscriptionPackageRepository subscriptionPackageRepository;

    // ✅ GET with optional paging + sorting
    @GetMapping
    public ResponseEntity<?> getAll(
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

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionPackage> getById(@PathVariable Long id) {
        return subscriptionPackageRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public SubscriptionPackage create(@RequestBody SubscriptionPackage pack) {
        return subscriptionPackageRepository.save(pack);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubscriptionPackage> update(@PathVariable Long id, @RequestBody SubscriptionPackage updated) {
        return subscriptionPackageRepository.findById(id)
                .map(existing -> {
                    existing.setName(updated.getName());
                    existing.setDescription(updated.getDescription());
                    existing.setPrice(updated.getPrice());
                    existing.setDurationDays(updated.getDurationDays());
                    existing.setFeatures(updated.getFeatures());
                    return ResponseEntity.ok(subscriptionPackageRepository.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!subscriptionPackageRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        subscriptionPackageRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
