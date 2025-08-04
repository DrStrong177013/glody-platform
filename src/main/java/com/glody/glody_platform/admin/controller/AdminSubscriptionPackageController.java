package com.glody.glody_platform.admin.controller;

import com.glody.glody_platform.admin.dto.SubscriptionPackageAdminDto;
import com.glody.glody_platform.admin.dto.SubscriptionPackageStatDto;
import com.glody.glody_platform.admin.dto.UserSubscriptionAdminDto;
import com.glody.glody_platform.admin.service.AdminSubscriptionPackageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Admin Subscription Packages", description = "Admin quản lý subscription package và dữ liệu liên quan.")
@RestController
@RequestMapping("/api/admin/packages")
public class AdminSubscriptionPackageController {
    @Autowired
    private AdminSubscriptionPackageService adminPackageService;

    @Operation(
            summary = "Lấy tất cả gói đăng ký (Admin)",
            description = "Trả về danh sách tất cả các gói đăng ký đang có trên hệ thống, bao gồm thông tin chi tiết, giá, thời hạn, thống kê số user, ..."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Thành công, trả về danh sách gói đăng ký")
    })
    @GetMapping
    public List<SubscriptionPackageAdminDto> getAllPackages() {
        return adminPackageService.getAllPackages();
    }

    @Operation(
            summary = "Lấy chi tiết gói đăng ký (Admin)",
            description = "Trả về thông tin chi tiết của một gói đăng ký theo id."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Thành công, trả về chi tiết gói đăng ký"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy gói đăng ký")
    })
    @GetMapping("/{id}")
    public SubscriptionPackageAdminDto getPackageDetail(
            @Parameter(description = "ID của gói đăng ký") @PathVariable Long id) {
        return adminPackageService.getPackageDetail(id);
    }

    @Operation(
            summary = "Thống kê user từng gói đăng ký (Admin)",
            description = "Thống kê số lượng user đã/đang đăng ký mỗi gói. Dùng cho mục đích phân tích, báo cáo."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Thành công, trả về thống kê từng gói")
    })
    @GetMapping("/stats")
    public List<SubscriptionPackageStatDto> getPackageStats() {
        return adminPackageService.getPackageStats();
    }

    @Operation(
            summary = "Lấy danh sách user theo gói (Admin)",
            description = "Trả về danh sách tất cả user đã/đang đăng ký một gói nhất định."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Thành công, trả về danh sách user"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy gói đăng ký")
    })
    @GetMapping("/{packageId}/users")
    public List<UserSubscriptionAdminDto> getUsersByPackage(
            @Parameter(description = "ID của gói đăng ký") @PathVariable Long packageId) {
        return adminPackageService.getUsersByPackage(packageId);
    }

    @Operation(
            summary = "Tạo mới gói đăng ký (Admin)",
            description = "Thêm mới một gói đăng ký vào hệ thống."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tạo mới thành công"),
            @ApiResponse(responseCode = "400", description = "Dữ liệu đầu vào không hợp lệ")
    })
    @PostMapping
    public SubscriptionPackageAdminDto createPackage(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Thông tin gói đăng ký cần tạo") @RequestBody SubscriptionPackageAdminDto dto) {
        return adminPackageService.createPackage(dto);
    }

    @Operation(
            summary = "Cập nhật gói đăng ký (Admin)",
            description = "Cập nhật thông tin một gói đăng ký theo id."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cập nhật thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy gói đăng ký")
    })
    @PutMapping("/{id}")
    public SubscriptionPackageAdminDto updatePackage(
            @Parameter(description = "ID của gói đăng ký") @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Thông tin gói đăng ký cần cập nhật") @RequestBody SubscriptionPackageAdminDto dto) {
        return adminPackageService.updatePackage(id, dto);
    }

    @Operation(
            summary = "Xóa gói đăng ký (Admin)",
            description = "Xóa một gói đăng ký khỏi hệ thống theo id."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Xóa thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy gói đăng ký")
    })
    @DeleteMapping("/{id}")
    public void deletePackage(
            @Parameter(description = "ID của gói đăng ký") @PathVariable Long id) {
        adminPackageService.deletePackage(id);
    }
}
