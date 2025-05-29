package com.glody.glody_platform.expert.controller;

import com.glody.glody_platform.expert.dto.AppointmentBookingDto;
import com.glody.glody_platform.expert.dto.AppointmentRequestDto;
import com.glody.glody_platform.expert.dto.AppointmentResponseDto;
import com.glody.glody_platform.expert.dto.AppointmentStatusUpdateDto;
import com.glody.glody_platform.expert.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
     * @param dto Thông tin lịch hẹn
     * @return Thông tin lịch hẹn sau khi tạo
     */
    @Operation(
            summary = "Đặt lịch hẹn (có tài khoản)",
            description = "Người dùng đã đăng nhập thực hiện đặt lịch với chuyên gia."
    )
    @PostMapping
    public ResponseEntity<AppointmentResponseDto> create(@RequestBody AppointmentRequestDto dto) {
        AppointmentResponseDto created = appointmentService.createAppointment(dto);
        return ResponseEntity.ok(created);
    }

    /**
     * Lấy danh sách lịch hẹn đã đặt của người dùng.
     *
     * @param userId ID người dùng
     * @return Danh sách lịch hẹn
     */
    @Operation(
            summary = "Lấy lịch hẹn của người dùng",
            description = "Trả về tất cả lịch hẹn đã đặt của một người dùng cụ thể theo userId."
    )
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AppointmentResponseDto>> getUserAppointments(@PathVariable Long userId) {
        List<AppointmentResponseDto> list = appointmentService.getAppointmentsByUser(userId);
        return ResponseEntity.ok(list);
    }

    /**
     * Lấy danh sách lịch hẹn của một chuyên gia.
     *
     * @param expertId ID chuyên gia
     * @return Danh sách lịch hẹn
     */
    @Operation(
            summary = "Lấy lịch hẹn của chuyên gia",
            description = "Trả về tất cả lịch hẹn đã được đặt với một chuyên gia cụ thể theo expertId."
    )
    @GetMapping("/expert/{expertId}")
    public ResponseEntity<List<AppointmentResponseDto>> getExpertAppointments(@PathVariable Long expertId) {
        List<AppointmentResponseDto> list = appointmentService.getAppointmentsByExpert(expertId);
        return ResponseEntity.ok(list);
    }

    /**
     * Cập nhật trạng thái lịch hẹn (xác nhận, hoàn thành, huỷ).
     *
     * @param appointmentId ID lịch hẹn
     * @param dto           Trạng thái mới
     * @return Thông báo cập nhật
     */
    @Operation(
            summary = "Cập nhật trạng thái lịch hẹn",
            description = "Chuyên gia hoặc admin có thể xác nhận, hoàn thành hoặc hủy lịch hẹn. (PENDING, CONFIRMED, COMPLETED, CANCELED)"
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
     *
     * @param dto Thông tin lịch hẹn
     * @return Phản hồi đặt lịch thành công
     */
    @Operation(
            summary = "Người dùng ẩn danh đặt lịch với chuyên gia",
            description = "Cho phép khách (chưa đăng nhập) đặt lịch hẹn nhanh với chuyên gia. Thông tin được lưu dưới dạng ẩn danh."
    )
    @PostMapping("/public")
    public ResponseEntity<String> anonymousBooking(@RequestBody AppointmentBookingDto dto) {
        appointmentService.createAnonymousAppointment(dto);
        return ResponseEntity.ok("📅 Đặt lịch hẹn thành công! Chuyên gia sẽ liên hệ với bạn.");
    }
}
