package com.glody.glody_platform.expert.service;

import com.glody.glody_platform.expert.dto.AppointmentRequestDto;
import com.glody.glody_platform.expert.dto.AppointmentResponseDto;
import com.glody.glody_platform.expert.dto.AppointmentStatusUpdateDto;
import com.glody.glody_platform.expert.entity.Appointment;
import com.glody.glody_platform.expert.entity.AppointmentStatus;
import com.glody.glody_platform.expert.repository.AppointmentRepository;
import com.glody.glody_platform.users.entity.User;
import com.glody.glody_platform.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    @Transactional
    public AppointmentResponseDto createAppointment(AppointmentRequestDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        User expert = userRepository.findById(dto.getExpertId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Expert not found"));

        Appointment appointment = new Appointment();
        appointment.setUser(user);
        appointment.setExpert(expert);
        appointment.setAppointmentTime(dto.getAppointmentTime());
        appointment.setStatus(AppointmentStatus.PENDING);

        appointmentRepository.save(appointment);
        return toDto(appointment);
    }

    public List<AppointmentResponseDto> getAppointmentsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        return appointmentRepository.findByUser(user).stream()
                .map(this::toDto)
                .toList();
    }

    public List<AppointmentResponseDto> getAppointmentsByExpert(Long expertId) {
        User expert = userRepository.findById(expertId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Expert not found"));

        return appointmentRepository.findByExpert(expert).stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional
    public void updateStatus(Long appointmentId, AppointmentStatusUpdateDto dto) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found"));

        appointment.setStatus(dto.getStatus());
        appointmentRepository.save(appointment);
    }

    private AppointmentResponseDto toDto(Appointment appointment) {
        AppointmentResponseDto dto = new AppointmentResponseDto();
        dto.setId(appointment.getId());
        dto.setAppointmentTime(appointment.getAppointmentTime());
        dto.setStatus(appointment.getStatus());
        dto.setUserFullName(appointment.getUser().getFullName());
        dto.setExpertFullName(appointment.getExpert().getFullName());
        return dto;
    }
}
