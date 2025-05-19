package com.glody.glody_platform.expert.repository;

import com.glody.glody_platform.expert.entity.ConsultationNote;
import com.glody.glody_platform.expert.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConsultationNoteRepository extends JpaRepository<ConsultationNote, Long> {
    Optional<ConsultationNote> findByAppointment(Appointment appointment);
}
