package com.glody.glody_platform.expert.controller;

import com.glody.glody_platform.expert.dto.ConsultationNoteDto;
import com.glody.glody_platform.expert.entity.ConsultationNote;
import com.glody.glody_platform.expert.service.ConsultationNoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller quản lý ghi chú tư vấn của chuyên gia cho từng cuộc hẹn.
 */
@RestController
@RequestMapping("/api/consultation-notes")
@RequiredArgsConstructor
@Tag(name = "Consultation Note Controller", description = "Quản lý ghi chú tư vấn từ chuyên gia")
public class ConsultationNoteController {

    private final ConsultationNoteService consultationNoteService;

    /**
     * Chuyên gia tạo ghi chú tư vấn cho lịch hẹn.
     *
     * @param dto Dữ liệu ghi chú
     * @return Ghi chú vừa được tạo
     */
    @Operation(summary = "Tạo ghi chú tư vấn cho lịch hẹn")
    @PostMapping
    public ResponseEntity<ConsultationNote> addNote(@RequestBody ConsultationNoteDto dto) {
        ConsultationNote note = consultationNoteService.addNote(dto);
        return ResponseEntity.ok(note);
    }

    /**
     * Lấy ghi chú tư vấn theo ID lịch hẹn.
     *
     * @param appointmentId ID lịch hẹn
     * @return Ghi chú tương ứng
     */
    @Operation(summary = "Lấy ghi chú tư vấn theo lịch hẹn")
    @GetMapping("/{appointmentId}")
    public ResponseEntity<ConsultationNote> getNote(@PathVariable Long appointmentId) {
        ConsultationNote note = consultationNoteService.getNoteByAppointment(appointmentId);
        return ResponseEntity.ok(note);
    }
}
