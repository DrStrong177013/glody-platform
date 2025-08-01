package com.glody.glody_platform.expert.controller;

import com.glody.glody_platform.expert.dto.AppointmentBookingDto;
import com.glody.glody_platform.expert.dto.AppointmentRequestDto;
import com.glody.glody_platform.expert.dto.AppointmentResponseDto;
import com.glody.glody_platform.expert.dto.AppointmentStatusUpdateDto;
import com.glody.glody_platform.expert.service.AppointmentService;
import com.glody.glody_platform.users.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller quản lý lịch hẹn giữa người dùng và chuyên gia.
 */
@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
@Tag(name = "Appointment Controller", description = "Quản lý đặt lịch và trạng thái lịch hẹn với chuyên gia")
public class AppointmentController {

    private final AppointmentService appointmentService;

    /**
     * Người dùng đã đăng nhập thực hiện đặt lịch với chuyên gia.
     *
     * @param currentUser Principal là User entity
     * @param dto         Thông tin lịch hẹn
     * @return Thông tin lịch hẹn sau khi tạo
     */
    @Operation(
            summary = "Đặt lịch hẹn có tài khoản (Student)",
            description = "Người dùng đã đăng nhập thực hiện đặt lịch với chuyên gia."
    )
    @PostMapping
    public ResponseEntity<AppointmentResponseDto> create(
            @AuthenticationPrincipal User currentUser,
            @Valid @RequestBody AppointmentRequestDto dto) {

        Long userId = currentUser.getId();
        AppointmentResponseDto created = appointmentService.createAppointment(userId, dto);
        return ResponseEntity.ok(created);
    }

    /**
     * Lấy danh sách lịch hẹn đã đặt của người dùng.
     */
    @Operation(
            summary = "Lấy lịch hẹn của người dùng hiện tại (Student)",
            description = "Trả về tất cả lịch hẹn đã đặt."
    )
    @GetMapping("/user/me")
    public ResponseEntity<List<AppointmentResponseDto>> getCurrentUserAppointments(@AuthenticationPrincipal User currentUser) {
        Long userId = currentUser.getId();
        List<AppointmentResponseDto> list = appointmentService.getAppointmentsByUser(userId);
        return ResponseEntity.ok(list);
    }

    /**
     * Lấy danh sách lịch hẹn của một chuyên gia.
     */
    @Operation(
            summary = "Lấy lịch hẹn của chuyên gia(Expert)",
            description = "Trả về tất cả lịch hẹn đã được đặt với một chuyên gia cụ thể."
    )
    @GetMapping("/expert/{expertId}")
    public ResponseEntity<List<AppointmentResponseDto>> getExpertAppointments(@PathVariable Long expertId) {
        List<AppointmentResponseDto> list = appointmentService.getAppointmentsByExpert(expertId);
        return ResponseEntity.ok(list);
    }

    /**
     * Cập nhật trạng thái lịch hẹn.
     */
    @Operation(
            summary = "Cập nhật trạng thái lịch hẹn (Expert).",
            description = "Chuyên gia có thể xác nhận, hoàn thành hoặc hủy lịch hẹn(ENUM AppointmentStatus: PENDING, CONFIRMED, COMPLETED,CANCELED"
    )
    @PatchMapping("/{appointmentId}/status")
    public ResponseEntity<String> updateStatus(
            @PathVariable Long appointmentId,
            @RequestBody AppointmentStatusUpdateDto dto) {

        appointmentService.updateStatus(appointmentId, dto);
        return ResponseEntity.ok("✅ Trạng thái lịch hẹn đã được cập nhật thành công.");
    }

    /**
     * Khách ẩn danh đặt lịch với chuyên gia.
     */
    @Operation(
            summary = "Người dùng ẩn danh đặt lịch với chuyên gia (UnAuth)",
            description = "Cho phép khách (chưa đăng nhập) đặt lịch hẹn nhanh với chuyên gia."
    )
    @PostMapping("/public")
    public ResponseEntity<String> anonymousBooking(@Valid @RequestBody AppointmentBookingDto dto) {
        appointmentService.createAnonymousAppointment(dto);
        return ResponseEntity.ok("📅 Đặt lịch hẹn thành công! Chuyên gia sẽ liên hệ với bạn.");
    }
}
