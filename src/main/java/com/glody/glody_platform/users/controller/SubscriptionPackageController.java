// SubscriptionPackageController.java
package com.glody.glody_platform.users.controller;

import com.glody.glody_platform.users.entity.SubscriptionPackage;
import com.glody.glody_platform.users.repository.SubscriptionPackageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscription-packages")
@RequiredArgsConstructor
public class SubscriptionPackageController {

    private final SubscriptionPackageRepository subscriptionPackageRepository;

    @GetMapping
    public List<SubscriptionPackage> getAll() {
        return subscriptionPackageRepository.findAll();
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
