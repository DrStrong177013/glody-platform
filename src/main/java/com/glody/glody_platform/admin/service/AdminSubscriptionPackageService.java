package com.glody.glody_platform.admin.service;

import com.glody.glody_platform.admin.dto.SubscriptionPackageAdminDto;
import com.glody.glody_platform.admin.dto.SubscriptionPackageStatDto;
import com.glody.glody_platform.admin.dto.UserSubscriptionAdminDto;
import com.glody.glody_platform.users.entity.SubscriptionPackage;
import com.glody.glody_platform.users.entity.User;
import com.glody.glody_platform.users.entity.UserSubscription;
import com.glody.glody_platform.users.repository.SubscriptionPackageRepository;
import com.glody.glody_platform.users.repository.UserSubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminSubscriptionPackageService {
    @Autowired
    private SubscriptionPackageRepository packageRepository;
    @Autowired
    private UserSubscriptionRepository userSubscriptionRepository;

    public List<SubscriptionPackageAdminDto> getAllPackages() {
        List<SubscriptionPackage> packages = packageRepository.findAll();
        // Gọi count từng loại nếu muốn
        List<Object[]> stats = userSubscriptionRepository.countUserStatsByPackage();

        return packages.stream().map(pkg -> {
            SubscriptionPackageAdminDto dto = new SubscriptionPackageAdminDto();
            dto.setId(pkg.getId());
            dto.setName(pkg.getName());
            dto.setDescription(pkg.getDescription());
            dto.setPrice(pkg.getPrice());
            dto.setDurationDays(pkg.getDurationDays());
            dto.setCreatedAt(pkg.getCreatedAt());
            dto.setStatus(pkg.getIsDeleted());

            // Ghép thêm thống kê nếu muốn
            Object[] stat = stats.stream()
                    .filter(s -> ((Long)s[0]).equals(pkg.getId()))
                    .findFirst().orElse(null);
            if(stat != null) {
                dto.setTotalUser(((Long)stat[1]).intValue());
                dto.setTotalActiveUser(((Long)stat[2]).intValue());
                dto.setTotalExpiredUser(((Long)stat[3]).intValue());
            } else {
                dto.setTotalUser(0);
                dto.setTotalActiveUser(0);
                dto.setTotalExpiredUser(0);
            }
            return dto;
        }).collect(Collectors.toList());
    }

    public SubscriptionPackageAdminDto getPackageDetail(Long id) {
        SubscriptionPackage pkg = packageRepository.findById(id).orElseThrow();
        SubscriptionPackageAdminDto dto = new SubscriptionPackageAdminDto();
        dto.setId(pkg.getId());
        dto.setName(pkg.getName());
        dto.setDescription(pkg.getDescription());
        dto.setPrice(pkg.getPrice());
        dto.setDurationDays(pkg.getDurationDays());
        dto.setCreatedAt(pkg.getCreatedAt());
        dto.setStatus(pkg.getIsDeleted());
        // thống kê nếu muốn
        return dto;
    }

    public List<SubscriptionPackageStatDto> getPackageStats() {
        List<Object[]> stats = userSubscriptionRepository.countUserStatsByPackage();
        return stats.stream().map(obj -> {
            SubscriptionPackageStatDto dto = new SubscriptionPackageStatDto();
            dto.setPackageId((Long) obj[0]);
            dto.setTotalUser(((Long) obj[1]).intValue());
            dto.setTotalActiveUser(((Long) obj[2]).intValue());
            dto.setTotalExpiredUser(((Long) obj[3]).intValue());
            // lấy tên package
            dto.setPackageName(packageRepository.findById(dto.getPackageId()).map(SubscriptionPackage::getName).orElse(""));
            return dto;
        }).collect(Collectors.toList());
    }

    public List<UserSubscriptionAdminDto> getUsersByPackage(Long packageId) {
        List<UserSubscription> userSubs = userSubscriptionRepository.findBySubscriptionPackageId(packageId);
        return userSubs.stream().map(us -> {
            User user = us.getUser();
            UserSubscriptionAdminDto dto = new UserSubscriptionAdminDto();
            dto.setId(us.getId());
            dto.setUserId(user.getId());
            dto.setUserName(user.getFullName());
            dto.setEmail(user.getEmail());
            dto.setPhone(user.getPhone());
            dto.setStartDate(us.getStartDate());
            dto.setEndDate(us.getEndDate());
            dto.setIsActive(us.getIsActive());
            dto.setPackageId(packageId);
            dto.setPackageName(us.getSubscriptionPackage().getName());
            return dto;
        }).collect(Collectors.toList());
    }

    // CRUD methods (create, update, delete)
    public SubscriptionPackageAdminDto createPackage(SubscriptionPackageAdminDto dto) {
        SubscriptionPackage pkg = new SubscriptionPackage();
        pkg.setName(dto.getName());
        pkg.setDescription(dto.getDescription());
        pkg.setPrice(dto.getPrice());
        pkg.setDurationDays(dto.getDurationDays());
        pkg = packageRepository.save(pkg);
        dto.setId(pkg.getId());
        return dto;
    }

    public SubscriptionPackageAdminDto updatePackage(Long id, SubscriptionPackageAdminDto dto) {
        SubscriptionPackage pkg = packageRepository.findById(id).orElseThrow();
        pkg.setName(dto.getName());
        pkg.setDescription(dto.getDescription());
        pkg.setPrice(dto.getPrice());
        pkg.setDurationDays(dto.getDurationDays());
        pkg = packageRepository.save(pkg);
        dto.setId(pkg.getId());
        return dto;
    }

    public void deletePackage(Long id) {
        packageRepository.deleteById(id);
    }
}
