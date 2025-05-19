package com.glody.glody_platform.matching.service;

import com.glody.glody_platform.catalog.entity.Program;
import com.glody.glody_platform.catalog.entity.Scholarship;
import com.glody.glody_platform.catalog.entity.University;
import com.glody.glody_platform.catalog.repository.ProgramRepository;
import com.glody.glody_platform.catalog.repository.ScholarshipRepository;
import com.glody.glody_platform.catalog.repository.UniversityRepository;
import com.glody.glody_platform.matching.dto.MatchingResultDto;
import com.glody.glody_platform.matching.dto.MatchingStatusUpdateRequest;
import com.glody.glody_platform.matching.entity.*;
import com.glody.glody_platform.matching.repository.*;
import com.glody.glody_platform.users.entity.User;
import com.glody.glody_platform.users.entity.UserProfile;
import com.glody.glody_platform.users.repository.UserProfileRepository;
import com.glody.glody_platform.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchingService {

    private final UserRepository userRepository;
    private final UserProfileRepository profileRepository;
    private final UniversityRepository universityRepository;
    private final ProgramRepository programRepository;
    private final ScholarshipRepository scholarshipRepository;
    private final MatchingSessionRepository sessionRepository;
    private final MatchingResultRepository resultRepository;
    private final MatchingScoreRepository scoreRepository;
    private final MatchingStatusRepository statusRepository;
    private final MatchingStatusLogRepository statusLogRepository;

    @Transactional
    public List<MatchingResultDto> matchForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User profile not found"));

        MatchingSession session = new MatchingSession();
        session.setUser(user);
        session.setStatus("COMPLETED");
        sessionRepository.save(session);

        List<University> universities = universityRepository.findByIsDeletedFalse();
        List<Program> programs = programRepository.findByIsDeletedFalse();
        List<Scholarship> scholarships = scholarshipRepository.findByIsDeletedFalseAndMinGpaLessThanEqual(profile.getGpa());

        List<MatchingResultDto> resultDtos = new ArrayList<>();

        for (University university : universities) {
            if (!university.getCountry().getName().equalsIgnoreCase(profile.getTargetCountry())) continue;

            List<Program> matchedPrograms = programs.stream()
                    .filter(p -> Objects.equals(p.getUniversity().getId(), university.getId()))
                    .filter(p -> matchMajor(p.getMajor(), profile.getMajor()))
                    .collect(Collectors.toList());

            for (Program program : matchedPrograms) {
                Optional<Scholarship> matchedScholarship = scholarships.stream()
                        .filter(s -> matchMajor(s.getApplicableMajors(), program.getMajor()))
                        .findFirst();

                MatchingResult result = new MatchingResult();
                result.setSession(session);
                result.setUniversity(university);
                result.setProgram(program);
                result.setScholarship(matchedScholarship.orElse(null));
                resultRepository.save(result);

                scoreRepository.save(score(result, "GPA", gpaScore(profile.getGpa(), matchedScholarship)));
                scoreRepository.save(score(result, "MAJOR", 100.0));
                scoreRepository.save(score(result, "COUNTRY", 100.0));

                MatchingResultDto dto = new MatchingResultDto();
                dto.setUniversityId(university.getId());
                dto.setUniversityName(university.getName());
                dto.setUniversityCountry(university.getCountry().getName());
                dto.setProgramId(program.getId());
                dto.setProgramName(program.getName());
                dto.setProgramMajor(program.getMajor());
                dto.setProgramDegreeType(program.getDegreeType());

                matchedScholarship.ifPresent(sch -> {
                    dto.setScholarshipId(sch.getId());
                    dto.setScholarshipName(sch.getName());
                });

                dto.setGpaScore(gpaScore(profile.getGpa(), matchedScholarship));
                dto.setMajorScore(100.0);
                dto.setCountryScore(100.0);

                resultDtos.add(dto);
            }
        }

        return resultDtos;
    }

    private MatchingScore score(MatchingResult result, String criterion, Double value) {
        MatchingScore s = new MatchingScore();
        s.setResult(result);
        s.setCriterion(criterion);
        s.setScore(value);
        return s;
    }

    private boolean matchMajor(String a, String b) {
        if (a == null || b == null) return false;
        return a.toLowerCase().contains(b.toLowerCase()) || b.toLowerCase().contains(a.toLowerCase());
    }

    private double gpaScore(Double userGpa, Optional<Scholarship> sch) {
        return sch.map(s -> {
            double base = s.getMinGpa() != null ? s.getMinGpa() : 0;
            return Math.min(100.0, (userGpa - base) * 25);
        }).orElse(0.0);
    }

    public void updateStatus(MatchingStatusUpdateRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        MatchingResult result = resultRepository.findById(request.getResultId())
                .orElseThrow(() -> new RuntimeException("Matching result not found"));

        MatchingStatus status = statusRepository.findByUserAndResult(user, result)
                .orElse(new MatchingStatus());
        status.setUser(user);
        status.setResult(result);
        status.setStatus(request.getStatus());
        statusRepository.save(status);

        MatchingStatusLog log = new MatchingStatusLog();
        log.setUser(user);
        log.setResult(result);
        log.setAction(request.getStatus());
        statusLogRepository.save(log);
    }

    public MatchingStatus getCurrentStatus(Long userId, Long resultId) {
        User user = userRepository.findById(userId).orElseThrow();
        MatchingResult result = resultRepository.findById(resultId).orElseThrow();
        return statusRepository.findByUserAndResult(user, result).orElse(null);
    }

    public List<MatchingStatusLog> getStatusLogs(Long userId, Long resultId) {
        User user = userRepository.findById(userId).orElseThrow();
        MatchingResult result = resultRepository.findById(resultId).orElseThrow();
        return statusLogRepository.findAllByUserAndResultOrderByCreatedAtDesc(user, result);
    }
}
