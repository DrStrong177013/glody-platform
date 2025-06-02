package com.glody.glody_platform.matching.service;

import com.glody.glody_platform.common.PageResponse;
import com.glody.glody_platform.matching.dto.MatchingHistoryDto;
import com.glody.glody_platform.matching.dto.ProgramMatchDto;
import com.glody.glody_platform.matching.entity.MatchingHistory;
import com.glody.glody_platform.matching.matcher.MatchCriterion;
import com.glody.glody_platform.matching.matcher.MatchResult;
import com.glody.glody_platform.matching.repository.MatchingHistoryRepository;
import com.glody.glody_platform.university.entity.Program;
import com.glody.glody_platform.university.repository.ProgramRepository;
import com.glody.glody_platform.users.entity.User;
import com.glody.glody_platform.users.entity.UserProfile;
import com.glody.glody_platform.users.repository.UserProfileRepository;
import com.glody.glody_platform.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProgramMatchingService {

    private final List<MatchCriterion> criteria;
    private final ProgramRepository programRepository;
    private final UserProfileRepository userProfileRepository;
    private final MatchingHistoryRepository matchingHistoryRepository;
    private final UserRepository userRepository; // để tìm User từ userId


    public PageResponse<ProgramMatchDto> findSuitablePrograms(
            Long userId,
            int page,
            int size,
            String sortBy,
            String sortDir,
            String keyword
    ) {
        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hồ sơ người dùng"));

        List<ProgramMatchDto> allPrograms = programRepository.findAll().stream()
                .map(program -> evaluateProgram(profile, program))
                .filter(dto -> dto.getMatchPercentage() > 0) // hoặc thêm điều kiện khác
                .filter(dto -> keyword == null || dto.getSchoolName().toLowerCase().contains(keyword.toLowerCase()))
                .sorted(getComparator(sortBy, sortDir))
                .collect(Collectors.toList());

        int totalElements = allPrograms.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);
        int fromIndex = Math.min(page * size, totalElements);
        int toIndex = Math.min(fromIndex + size, totalElements);

        List<ProgramMatchDto> pagedItems = allPrograms.subList(fromIndex, toIndex);
        saveProgramMatchingHistory(userId, pagedItems);

        PageResponse.PageInfo pageInfo = new PageResponse.PageInfo(
                page,
                size,
                totalPages,
                totalElements,
                page < totalPages - 1,
                page > 0
        );

        return new PageResponse<>(pagedItems, pageInfo);
    }
    private void saveProgramMatchingHistory(Long userId, List<ProgramMatchDto> dtos) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        List<MatchingHistory> histories = dtos.stream()
                .map(dto -> {
                    MatchingHistory h = new MatchingHistory();
                    h.setUser(user);
                    h.setReferenceId(dto.getId());
                    h.setMatchType("PROGRAM");
                    h.setMatchPercentage(dto.getMatchPercentage());
                    h.setAdditionalInfo(dto.getSchoolName());
                    return h;
                })
                .collect(Collectors.toList());

        matchingHistoryRepository.saveAll(histories);
    }


    private Comparator<ProgramMatchDto> getComparator(String sortBy, String sortDir) {
        Comparator<ProgramMatchDto> comparator = Comparator.comparing(ProgramMatchDto::getMatchPercentage);
        if ("schoolName".equals(sortBy)) {
            comparator = Comparator.comparing(ProgramMatchDto::getSchoolName);
        }

        return "desc".equalsIgnoreCase(sortDir) ? comparator.reversed() : comparator;
    }


    private ProgramMatchDto evaluateProgram(UserProfile profile, Program program) {
        int matched = 0;
        List<String> reasons = new ArrayList<>();

        for (MatchCriterion criterion : criteria) {
            MatchResult result = criterion.evaluate(profile, program);
            if (result.isMatched()) matched++;
            reasons.add(result.getReason());
        }

        int percentage = (int) ((matched / (double) criteria.size()) * 100);
        ProgramMatchDto dto = toDto(program, percentage);
        dto.setMatchReasons(reasons);
        return dto;
    }


    private ProgramMatchDto toDto(Program program, int percentage) {
        ProgramMatchDto dto = new ProgramMatchDto();
        dto.setId(program.getId());
        dto.setSchoolName(program.getSchool().getName());
        dto.setSchoolLogoUrl(program.getSchool().getLogoUrl());
        dto.setLevel(program.getLevel().name());
        dto.setLanguage(program.getLanguage().name());
        dto.setMajors(program.getMajors());
        dto.setTuitionFee(program.getTuitionFee());
        dto.setLivingCost(program.getLivingCost());
        dto.setDormFee(program.getDormFee());
        dto.setScholarshipSupport(program.getScholarshipSupport());
        dto.setMatchPercentage(percentage);
        return dto;
    }


}
