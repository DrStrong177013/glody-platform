package com.glody.glody_platform.expert.controller;

import com.glody.glody_platform.expert.dto.AppointmentRequestDto;
import com.glody.glody_platform.expert.dto.AppointmentResponseDto;
import com.glody.glody_platform.expert.dto.AppointmentStatusUpdateDto;
import com.glody.glody_platform.expert.entity.Appointment;
import com.glody.glody_platform.expert.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<AppointmentResponseDto> create(@RequestBody AppointmentRequestDto dto) {
        return ResponseEntity.ok(appointmentService.createAppointment(dto));
    }

    @GetMapping("/user/{userId}")
    public List<AppointmentResponseDto> getUserAppointments(@PathVariable Long userId) {
        return appointmentService.getAppointmentsByUser(userId);
    }

    @GetMapping("/expert/{expertId}")
    public List<AppointmentResponseDto> getExpertAppointments(@PathVariable Long expertId) {
        return appointmentService.getAppointmentsByExpert(expertId);
    }

    @PatchMapping("/{appointmentId}/status")
    public ResponseEntity<String> updateStatus(@PathVariable Long appointmentId,
                                               @RequestBody AppointmentStatusUpdateDto dto) {
        appointmentService.updateStatus(appointmentId, dto);
        return ResponseEntity.ok("Appointment status updated successfully");
    }
}
