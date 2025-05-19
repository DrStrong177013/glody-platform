package com.glody.glody_platform.expert.service;

import com.glody.glody_platform.expert.dto.ConsultationNoteDto;
import com.glody.glody_platform.expert.entity.Appointment;
import com.glody.glody_platform.expert.entity.ConsultationNote;
import com.glody.glody_platform.expert.repository.AppointmentRepository;
import com.glody.glody_platform.expert.repository.ConsultationNoteRepository;
import com.glody.glody_platform.users.entity.User;
import com.glody.glody_platform.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConsultationNoteService {

    private final ConsultationNoteRepository noteRepository;
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    @Transactional
    public ConsultationNote addNote(ConsultationNoteDto dto) {
        Appointment appointment = appointmentRepository.findById(dto.getAppointmentId())
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        User expert = userRepository.findById(dto.getExpertId())
                .orElseThrow(() -> new RuntimeException("Expert not found"));

        ConsultationNote note = new ConsultationNote();
        note.setAppointment(appointment);
        note.setExpert(expert);
        note.setNote(dto.getNote());

        return noteRepository.save(note);
    }

    public ConsultationNote getNoteByAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        return noteRepository.findByAppointment(appointment)
                .orElse(null);
    }
}
