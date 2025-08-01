package com.glody.glody_platform.matchingV2.service;

import com.glody.glody_platform.matchingV2.criteria.ProgramSearchCriteria;
import com.glody.glody_platform.matchingV2.dto.MatchingSummary;
import com.glody.glody_platform.matchingV2.dto.ProgramDetailDto;
import com.glody.glody_platform.matchingV2.dto.ProgramDto;
import com.glody.glody_platform.matchingV2.dto.ProgramSearchResponse;
import com.glody.glody_platform.matchingV2.engine.ScoringEngine;
import com.glody.glody_platform.matchingV2.specification.ProgramSpecification;
import com.glody.glody_platform.university.entity.Program;
import com.glody.glody_platform.users.entity.UserProfile;
import com.glody.glody_platform.university.repository.ProgramRepository;
import com.glody.glody_platform.users.repository.UserProfileRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProgramSearchService {

    private final ProgramRepository    programRepo;
    private final UserProfileRepository profileRepo;
    private final ScoringEngine         scoringEngine;

    public ProgramSearchService(
            ProgramRepository programRepo,
            UserProfileRepository profileRepo,
            ScoringEngine scoringEngine
    ) {
        this.programRepo   = programRepo;
        this.profileRepo   = profileRepo;
        this.scoringEngine = scoringEngine;
    }

    /**
     * Search có phân trang, chỉ trả về ProgramDto cơ bản.
     */
    public Page<ProgramDto> search(
            Long userId,
            ProgramSearchCriteria criteria,
            Pageable pageable
    ) {
        UserProfile profile = loadProfile(userId);
        Specification<Program> spec = ProgramSpecification.matches(criteria, profile);

        return programRepo.findAll(spec, pageable)
                .map(program -> toDto(program, profile, criteria));
    }

    /**
     * Search không phân trang, trả về List<ProgramDto>.
     */
    public List<ProgramDto> searchAll(
            Long userId,
            ProgramSearchCriteria criteria
    ) {
        UserProfile profile = loadProfile(userId);
        Specification<Program> spec = ProgramSpecification.matches(criteria, profile);

        return programRepo.findAll(spec).stream()
                .map(program -> toDto(program, profile, criteria))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * Search mở rộng, trả về summary + chi tiết + global tips.
     */
    public ProgramSearchResponse searchExtended(
            Long userId,
            ProgramSearchCriteria criteria
    ) {
        UserProfile profile = loadProfile(userId);
        List<Program> candidates = programRepo.findAll(
                ProgramSpecification.matches(criteria, profile)
        );

        // build detail list
        List<ProgramDetailDto> details = new ArrayList<>();
        for (Program p : candidates) {
            ProgramDto base = toDto(p, profile, criteria);
            if (base == null) {
                // bị filter bởi minGpa
                continue;
            }

            double scorePct = scoringEngine.calculateScore(profile, p);
            Map<String, Double> scores = scoringEngine.getStrategies().stream()
                    .collect(Collectors.toMap(
                            s -> s.getName(),
                            s -> s.score(profile, p),
                            (a, b) -> a,
                            LinkedHashMap::new
                    ));

            List<String> missing = scores.entrySet().stream()
                    .filter(e -> e.getValue() < 1.0)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

            List<String> recs = buildRecommendations(p, scores);

            details.add(ProgramDetailDto.builder()
                    .id(base.getId())
                    .title(base.getTitle())
                    .schoolName(base.getSchoolName())
                    .tuitionFee(base.getTuitionFee())
                    .matchPercentage(scorePct)
                    .criteriaScores(scores)
                    .missingRequirements(missing)
                    .applicationDeadline(p.getRequirement().getDeadline())
                    .detailsUrl("/programs/" + p.getId())
                    .recommendations(recs)
                    .build()
            );
        }

        // summary
        int total = details.size();
        double avg = details.stream()
                .mapToDouble(ProgramDetailDto::getMatchPercentage)
                .average().orElse(0.0);
        int matched = (int) details.stream()
                .filter(d -> d.getMatchPercentage() >= 50.0)
                .count();
        MatchingSummary summary = MatchingSummary.builder()
                .totalPrograms(total)
                .matchedCount(matched)
                .averageMatch(BigDecimal.valueOf(avg)
                        .setScale(2, RoundingMode.HALF_UP)
                        .doubleValue())
                .build();

        List<String> globalTips = List.of(
                "Xem xét cập nhật hồ sơ để tăng tỉ lệ phù hợp."
        );

        return new ProgramSearchResponse(summary, details, globalTips);
    }

    //───────────────────────────────────────────────────────────────────────────────

    private UserProfile loadProfile(Long userId) {
        return profileRepo.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found for userId=" + userId));
    }

    /**
     * Chuyển Program + Profile + Criteria thành DTO cơ bản, hoặc null nếu bị filter minGpa.
     */
    private ProgramDto toDto(Program program, UserProfile profile, ProgramSearchCriteria criteria) {
        // xử lý GPA filter & normalize
        if (!passesGpaFilter(program, profile, criteria)) {
            return null;
        }

        double score = scoringEngine.calculateScore(profile, program);
        Double tuition = parseMoney(program.getTuitionFee());

        return ProgramDto.builder()
                .id(program.getId())
                .title(Optional.ofNullable(program.getLevel())
                        .map(Enum::toString).orElse(null))
                .schoolName(program.getSchool().getName())
                .tuitionFee(tuition)
                .matchPercentage(score)
                .build();
    }

    /**
     * Kiểm tra xem user có đạt minGpa không (sau khi normalize theo scale).
     */
    private boolean passesGpaFilter(Program program, UserProfile profile, ProgramSearchCriteria criteria) {
        if (criteria.getMinGpa() == null) {
            return true;
        }
        double userScale = Optional.ofNullable(criteria.getGpaScale())
                .orElse(profile.getGpaScale());

        String raw = program.getRequirement().getGpaRequirement();
        if (raw == null || !raw.contains("/")) {
            // không có slash thì không parse được
            return false;
        }
        // chỉ giữ chữ số, dấu '.' và '/'
        String cleaned = raw.replaceAll("[^\\d./]", "");
        String[] parts = cleaned.split("/");
        if (parts.length != 2) {
            return false;
        }

        try {
            double reqValue = Double.parseDouble(parts[0]);
            double reqScale = Double.parseDouble(parts[1]);

            double normalizedUserGpa = profile.getGpa() / userScale * reqScale;
            return normalizedUserGpa >= criteria.getMinGpa();
        } catch (NumberFormatException ex) {
            // hoặc log ex.getMessage() vào response cho client
            throw new IllegalArgumentException("Invalid GPA format: " + raw);
        }
    }


    /**
     * Sinh recommendation dựa trên từng criterion score < 1.0.
     */
    private List<String> buildRecommendations(
            Program program,
            Map<String, Double> scores
    ) {
        var recs = new ArrayList<String>();

        if (scores.getOrDefault("GPA", 1.0) < 1.0) {
            String reqGpa = program.getRequirement().getGpaRequirement().split("/")[0];
            recs.add("Cân nhắc nâng GPA lên ít nhất " + reqGpa);
        }
        if (scores.getOrDefault("LANGUAGE", 1.0) < 1.0) {
            recs.add("Phải đạt chứng chỉ "
                    + program.getRequirement().getLanguageRequirement());
        }
        if (scores.getOrDefault("MAJOR", 1.0) < 1.0) {
            var majors = program.getMajors();
            if (majors != null && !majors.isEmpty()) {
                recs.add("Cập nhật chuyên ngành thành " + String.join(", ", majors));
            }
        }
        // ... thêm nếu cần cho các strategy khác
        return recs;
    }

    /**
     * Chuyển chuỗi như "£24,000/năm" thành 24000.0
     */
    private Double parseMoney(String s) {
        if (s == null) return null;
        String digits = s.replaceAll("[^\\d.]", "");
        try {
            return Double.valueOf(digits);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
