package com.glody.glody_platform.expert.service;

import com.glody.glody_platform.expert.dto.AppointmentBookingDto;
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
    public AppointmentResponseDto createAppointment(Long userId,AppointmentRequestDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        User expert = userRepository.findById(dto.getExpertId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Expert not found"));

        Appointment appointment = new Appointment();
        appointment.setUser(user);
        appointment.setEmail(user.getEmail());
        appointment.setExpert(expert);
        appointment.setFullName(user.getFullName());
        appointment.setPhone(user.getPhone());
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
    @Transactional
    public Appointment createAnonymousAppointment(AppointmentBookingDto dto) {
        User expert = userRepository.findById(dto.getExpertId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Expert not found"));

        // ✅ Không cho đặt lịch trong quá khứ
        if (dto.getAppointmentDateTime().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không thể đặt lịch trong quá khứ");
        }

        // ✅ Kiểm tra trùng giờ
        boolean exists = appointmentRepository.existsByExpertAndAppointmentTime(
                expert, dto.getAppointmentDateTime());
        if (exists) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Chuyên gia đã có lịch tại thời gian này");
        }

        Appointment appointment = new Appointment();
        appointment.setExpert(expert);
        appointment.setAppointmentTime(dto.getAppointmentDateTime());
        appointment.setFullName(dto.getFullName());
        appointment.setEmail(dto.getEmail());
        appointment.setPhone(dto.getPhone());
        appointment.setStatus(AppointmentStatus.PENDING);


        appointmentRepository.save(appointment);


//        sendConfirmationEmail(dto);

        return appointment;
    }
    private void sendConfirmationEmail(AppointmentBookingDto dto) {
        System.out.printf("""
        📧 GỬI EMAIL XÁC NHẬN
        -----------------------------------
        To: %s
        Nội dung: Xin chào %s,
        Bạn đã đặt lịch hẹn thành công với chuyên gia (ID: %s)
        Vào lúc: %s
        Trạng thái: PENDING
        -----------------------------------
        """, dto.getEmail(), dto.getFullName(), dto.getExpertId(), dto.getAppointmentDateTime());
    }



    private AppointmentResponseDto toDto(Appointment appointment) {
        AppointmentResponseDto dto = new AppointmentResponseDto();
        dto.setId(appointment.getId());
        dto.setAppointmentTime(appointment.getAppointmentTime());
        dto.setStatus(appointment.getStatus());

        // ✅ An toàn cho cả lịch ẩn danh
        dto.setUserFullName(
                appointment.getUser() != null
                        ? appointment.getUser().getFullName()
                        : appointment.getFullName() // tên nhập tay khi đặt ẩn danh
        );

        dto.setExpertFullName(
                appointment.getExpert() != null
                        ? appointment.getExpert().getFullName()
                        : "Chuyên gia không xác định"
        );
        if (appointment.getUser() != null) {
            dto.setIsAnonymous(false);
        }
        else {
            dto.setIsAnonymous(true);
        }

        return dto;
    }

}
