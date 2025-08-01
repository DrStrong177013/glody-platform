package com.glody.glody_platform.university.controller;

import com.glody.glody_platform.university.dto.ProgramRequirementDto;
import com.glody.glody_platform.university.service.ProgramRequirementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/program-requirements")
@RequiredArgsConstructor
@Tag(name = "Program Requirement Controller", description = "Đang sửa")
public class ProgramRequirementController {

    private final ProgramRequirementService service;

    @GetMapping("/{programId}")
    @Operation(summary = "Lấy yêu cầu hồ sơ của chương trình")
    public ProgramRequirementDto get(@PathVariable Long programId) {
        return service.getByProgramId(programId);
    }

    @PostMapping("/{programId}")
    @Operation(summary = "Tạo hoặc cập nhật yêu cầu hồ sơ cho chương trình")
    public ProgramRequirementDto createOrUpdate(@PathVariable Long programId,
                                                @RequestBody ProgramRequirementDto dto) {
        return service.createOrUpdate(programId, dto);
    }

    @DeleteMapping("/{programId}")
    @Operation(summary = "Xoá yêu cầu hồ sơ khỏi chương trình")
    public String delete(@PathVariable Long programId) {
        service.deleteByProgramId(programId);
        return "Deleted";
    }
}
