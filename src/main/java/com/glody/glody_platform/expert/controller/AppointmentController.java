package com.glody.glody_platform.expert.controller;

import com.glody.glody_platform.expert.dto.AppointmentRequestDto;
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
    public Appointment create(@RequestBody AppointmentRequestDto dto) {
        return appointmentService.createAppointment(dto);
    }

    @GetMapping("/user/{userId}")
    public List<Appointment> getUserAppointments(@PathVariable Long userId) {
        return appointmentService.getAppointmentsByUser(userId);
    }

    @GetMapping("/expert/{expertId}")
    public List<Appointment> getExpertAppointments(@PathVariable Long expertId) {
        return appointmentService.getAppointmentsByExpert(expertId);
    }
}
