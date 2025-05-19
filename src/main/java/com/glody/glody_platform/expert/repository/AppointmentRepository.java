package com.glody.glody_platform.expert.repository;

import com.glody.glody_platform.expert.entity.Appointment;
import com.glody.glody_platform.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByUser(User user);
    List<Appointment> findByExpert(User expert);
    List<Appointment> findByExpertAndAppointmentTimeAfter(User expert, LocalDateTime dateTime);
}
