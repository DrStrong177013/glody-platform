package com.glody.glody_platform.expert.service;

import com.glody.glody_platform.expert.dto.AppointmentRequestDto;
import com.glody.glody_platform.expert.entity.Appointment;
import com.glody.glody_platform.expert.repository.AppointmentRepository;
import com.glody.glody_platform.users.entity.User;
import com.glody.glody_platform.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    @Transactional
    public Appointment createAppointment(AppointmentRequestDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        User expert = userRepository.findById(dto.getExpertId())
                .orElseThrow(() -> new RuntimeException("Expert not found"));

        Appointment appointment = new Appointment();
        appointment.setUser(user);
        appointment.setExpert(expert);
        appointment.setAppointmentTime(dto.getAppointmentTime());
        appointment.setStatus("PENDING");

        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAppointmentsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return appointmentRepository.findByUser(user);
    }

    public List<Appointment> getAppointmentsByExpert(Long expertId) {
        User expert = userRepository.findById(expertId)
                .orElseThrow(() -> new RuntimeException("Expert not found"));
        return appointmentRepository.findByExpert(expert);
    }
}
