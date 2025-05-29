// DataInitializer.java
package com.glody.glody_platform.config;

import com.glody.glody_platform.users.entity.Role;
import com.glody.glody_platform.users.entity.SubscriptionPackage;
import com.glody.glody_platform.users.repository.RoleRepository;
import com.glody.glody_platform.users.repository.SubscriptionPackageRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final RoleRepository roleRepository;
    private final SubscriptionPackageRepository subscriptionPackageRepository;

    @PostConstruct
    public void initDefaults() {
        initRoles();
        initSubscriptionPackages();
    }

    private void initRoles() {
        try {
            if (roleRepository.findByRoleName("STUDENT").isEmpty()) {
                Role student = new Role();
                student.setRoleName("STUDENT");
                roleRepository.save(student);
            }
            if (roleRepository.findByRoleName("EXPERT").isEmpty()) {
                Role expert = new Role();
                expert.setRoleName("EXPERT");
                roleRepository.save(expert);
            }

            if (roleRepository.findByRoleName("ADMIN").isEmpty()) {
                Role admin = new Role();
                admin.setRoleName("ADMIN");
                roleRepository.save(admin);
            }
        } catch (DataAccessException ex) {
            System.err.println("⚠ Bảng roles chưa được khởi tạo – bỏ qua việc tạo dữ liệu mẫu: " + ex.getMessage());
        } catch (Exception ex) {
            System.err.println("❌ Lỗi khởi tạo dữ liệu role mặc định: " + ex.getMessage());
        }
    }

    private void initSubscriptionPackages() {
        try {
            if (subscriptionPackageRepository.findByName("FREE").isEmpty()) {
                SubscriptionPackage free = new SubscriptionPackage();
                free.setName("FREE");
                free.setDescription("Gói cơ bản miễn phí");
                free.setPrice(0.0);
                free.setDurationDays(30);
                free.setFeatures("Tư vấn cơ bản, 1 lần/tuần");
                subscriptionPackageRepository.save(free);
            }

            if (subscriptionPackageRepository.findByName("BASIC").isEmpty()) {
                SubscriptionPackage basic = new SubscriptionPackage();
                basic.setName("BASIC");
                basic.setDescription("Gói cơ bản trả phí");
                basic.setPrice(199000.0);
                basic.setDurationDays(30);
                basic.setFeatures("Tư vấn mở rộng, tài liệu học tập");
                subscriptionPackageRepository.save(basic);
            }

            if (subscriptionPackageRepository.findByName("PREMIUM").isEmpty()) {
                SubscriptionPackage premium = new SubscriptionPackage();
                premium.setName("PREMIUM");
                premium.setDescription("Gói cao cấp đầy đủ tính năng");
                premium.setPrice(399000.0);
                premium.setDurationDays(90);
                premium.setFeatures("Tư vấn 1-1, ưu tiên hỗ trợ, bộ tài liệu chuyên sâu");
                subscriptionPackageRepository.save(premium);
            }
        } catch (Exception ex) {
            System.err.println("❌ Lỗi khởi tạo gói dịch vụ mặc định: " + ex.getMessage());
        }
    }
}