package com.glody.glody_platform.matching.service;

import com.glody.glody_platform.common.PageResponse;
import com.glody.glody_platform.matching.dto.ScholarshipMatchDto;
import com.glody.glody_platform.matching.matcher.scholarship.ScholarshipMatchCriterion;
import com.glody.glody_platform.university.entity.Program;
import com.glody.glody_platform.university.entity.Scholarship;
import com.glody.glody_platform.university.repository.ScholarshipRepository;
import com.glody.glody_platform.users.entity.User;
import com.glody.glody_platform.users.entity.UserProfile;
import com.glody.glody_platform.users.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScholarshipMatchingService {

    private final UserProfileRepository userProfileRepository;
    private final ScholarshipRepository scholarshipRepository;
    private final MatchingHistoryService matchingHistoryService;
    private final List<ScholarshipMatchCriterion> criteria;

    public PageResponse<ScholarshipMatchDto> suggestScholarships(
            Long userId,
            int page,
            int size,
            String sortBy,
            String sortDir,
            String keyword
    ) {
        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hồ sơ người dùng"));

        List<Scholarship> scholarships = scholarshipRepository.findAllWithSchoolAndPrograms();

        List<ScholarshipMatchDto> allMatches = scholarships.stream()
                .map(scholarship -> evaluateMatch(profile, scholarship))
                .filter(dto -> dto.getApplicationDeadline() == null || !dto.getApplicationDeadline().isBefore(LocalDate.now()))
                .filter(dto -> keyword == null || dto.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                .sorted(getComparator(sortBy, sortDir))
                .collect(Collectors.toList());

        int total = allMatches.size();
        int totalPages = (int) Math.ceil((double) total / size);
        int fromIndex = Math.min(page * size, total);
        int toIndex = Math.min(fromIndex + size, total);

        List<ScholarshipMatchDto> paged = allMatches.subList(fromIndex, toIndex);
        matchingHistoryService.saveScholarshipMatchList(profile.getUser(), paged);

        PageResponse.PageInfo pageInfo = new PageResponse.PageInfo(
                page, size, totalPages, total,
                page < totalPages - 1, page > 0
        );

        return new PageResponse<>(paged, pageInfo);
    }

    private Comparator<ScholarshipMatchDto> getComparator(String sortBy, String sortDir) {
        Comparator<ScholarshipMatchDto> comparator = Comparator.comparing(ScholarshipMatchDto::getMatchPercentage);
        if ("title".equalsIgnoreCase(sortBy)) {
            comparator = Comparator.comparing(ScholarshipMatchDto::getTitle);
        } else if ("deadline".equalsIgnoreCase(sortBy)) {
            comparator = Comparator.comparing(ScholarshipMatchDto::getApplicationDeadline, Comparator.nullsLast(Comparator.naturalOrder()));
        }

        return "desc".equalsIgnoreCase(sortDir) ? comparator.reversed() : comparator;
    }


    private ScholarshipMatchDto evaluateMatch(UserProfile profile, Scholarship scholarship) {
        Program program = findMatchingProgram(profile, scholarship);

        int total = 0;
        List<String> reasons = new ArrayList<>();

        for (ScholarshipMatchCriterion criterion : criteria) {
            int score = criterion.score(profile, scholarship, program);
            total += score;
            reasons.add(criterion.getReason());
        }

        ScholarshipMatchDto dto = new ScholarshipMatchDto();
        dto.setId(scholarship.getId());
        dto.setTitle(scholarship.getTitle());
        dto.setSponsor(scholarship.getSponsor());
        dto.setValue(scholarship.getValue());
        dto.setSchoolName(scholarship.getSchool() != null ? scholarship.getSchool().getName() : null);
        dto.setSchoolLogoUrl(scholarship.getSchool() != null ? scholarship.getSchool().getLogoUrl() : null);
        dto.setApplicationDeadline(scholarship.getApplicationDeadline());
        dto.setMatchPercentage(Math.min(total, 100));
        dto.setHighlightedConditions(String.join(", ", scholarship.getConditions() != null ? scholarship.getConditions() : List.of()));
        dto.setMatchReasons(reasons);
        return dto;
    }

    private Program findMatchingProgram(UserProfile profile, Scholarship scholarship) {
        if (scholarship.getSchool() == null || profile.getMajor() == null) return null;

        return scholarship.getSchool().getPrograms().stream()
                .filter(p -> p.getMajors() != null &&
                        p.getMajors().stream().anyMatch(
                                m -> m.toLowerCase().contains(profile.getMajor().toLowerCase())))
                .findFirst()
                .orElse(null);
    }
}
