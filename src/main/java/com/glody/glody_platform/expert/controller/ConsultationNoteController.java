package com.glody.glody_platform.expert.controller;

import com.glody.glody_platform.expert.dto.ConsultationNoteDto;
import com.glody.glody_platform.expert.entity.ConsultationNote;
import com.glody.glody_platform.expert.service.ConsultationNoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/consultation-notes")
@RequiredArgsConstructor
public class ConsultationNoteController {

    private final ConsultationNoteService consultationNoteService;

    @PostMapping
    public ConsultationNote addNote(@RequestBody ConsultationNoteDto dto) {
        return consultationNoteService.addNote(dto);
    }

    @GetMapping("/{appointmentId}")
    public ConsultationNote getNote(@PathVariable Long appointmentId) {
        return consultationNoteService.getNoteByAppointment(appointmentId);
    }
}
