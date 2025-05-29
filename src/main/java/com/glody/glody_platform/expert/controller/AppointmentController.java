package com.glody.glody_platform.expert.controller;

import com.glody.glody_platform.expert.dto.AppointmentBookingDto;
import com.glody.glody_platform.expert.dto.AppointmentRequestDto;
import com.glody.glody_platform.expert.dto.AppointmentResponseDto;
import com.glody.glody_platform.expert.dto.AppointmentStatusUpdateDto;
import com.glody.glody_platform.expert.entity.Appointment;
import com.glody.glody_platform.expert.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Operation(
            summary = "Đặt lịch hẹn (có tài khoản)",
            description = "Người dùng đã đăng nhập thực hiện đặt lịch với chuyên gia."
    )
    @PostMapping
    public ResponseEntity<AppointmentResponseDto> create(@RequestBody AppointmentRequestDto dto) {
        return ResponseEntity.ok(appointmentService.createAppointment(dto));
    }

    @Operation(
            summary = "Lấy lịch hẹn của người dùng",
            description = "Trả về tất cả lịch hẹn đã đặt của một người dùng cụ thể theo userId."
    )
    @GetMapping("/user/{userId}")
    public List<AppointmentResponseDto> getUserAppointments(@PathVariable Long userId) {
        return appointmentService.getAppointmentsByUser(userId);
    }

    @Operation(
            summary = "Lấy lịch hẹn của chuyên gia",
            description = "Trả về tất cả lịch hẹn đã được đặt với một chuyên gia cụ thể theo expertId."
    )
    @GetMapping("/expert/{expertId}")
    public List<AppointmentResponseDto> getExpertAppointments(@PathVariable Long expertId) {
        return appointmentService.getAppointmentsByExpert(expertId);
    }

    @Operation(
            summary = "Cập nhật trạng thái lịch hẹn",
            description = "Chuyên gia hoặc admin có thể xác nhận, hoàn thành hoặc hủy lịch hẹn. ( PENDING, CONFIRMED, COMPLETED, CANCELED)"

    )
    @PatchMapping("/{appointmentId}/status")
    public ResponseEntity<String> updateStatus(@PathVariable Long appointmentId,
                                               @RequestBody AppointmentStatusUpdateDto dto) {
        appointmentService.updateStatus(appointmentId, dto);
        return ResponseEntity.ok("Appointment status updated successfully");
    }

    @Operation(
            summary = "Người dùng ẩn danh đặt lịch với chuyên gia",
            description = "Cho phép khách (chưa đăng nhập) đặt lịch hẹn nhanh với chuyên gia. Thông tin được lưu dưới dạng ẩn danh."
    )
    @PostMapping("/public")
    public ResponseEntity<String> anonymousBooking(@RequestBody AppointmentBookingDto dto) {
        appointmentService.createAnonymousAppointment(dto);
        return ResponseEntity.ok("Đặt lịch hẹn thành công! Chuyên gia sẽ liên hệ với bạn.");
    }
}

