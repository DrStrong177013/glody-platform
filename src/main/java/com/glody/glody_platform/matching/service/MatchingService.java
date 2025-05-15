package com.glody.glody_platform.matching.service;

import com.glody.glody_platform.matching.dto.*;
import com.glody.glody_platform.matching.entity.*;
import com.glody.glody_platform.matching.repository.*;
import com.glody.glody_platform.users.entity.User;
import com.glody.glody_platform.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchingService {

    private final MatchingSessionRepository sessionRepo;
    private final MatchingResultRepository resultRepo;
    private final MatchingScoreRepository scoreRepo;
    private final MatchingStatusLogRepository statusLogRepo;
    private final UserRepository userRepository;

    @Transactional
    public MatchingSessionDto createMatchingSession(Long userId, MatchingSessionDto sessionDto) {
        User user = userRepository.findById(userId.toString())
                .orElseThrow(() -> new RuntimeException("User not found"));

        final MatchingSession session = sessionRepo.save(createNewSession(user));

        List<MatchingResult> results = sessionDto.getResults().stream().map(dto -> {
            MatchingResult result = new MatchingResult();
            result.setSession(session);
            result.setScholarshipId(dto.getScholarshipId());
            result.setType(dto.getType());
            result.setTotalScore(dto.getTotalScore());
            final MatchingResult savedResult = resultRepo.save(result);

            if (dto.getScores() != null) {
                List<MatchingScore> scores = dto.getScores().stream().map(s -> {
                    MatchingScore score = new MatchingScore();
                    score.setResult(savedResult);
                    score.setCriteria(s.getCriteria());
                    score.setScore(s.getScore());
                    score.setReason(s.getReason());
                    return score;
                }).collect(Collectors.toList());
                scoreRepo.saveAll(scores);
            }

            if (dto.getStatusLog() != null) {
                MatchingStatusLog log = new MatchingStatusLog();
                log.setResult(savedResult);
                log.setStatus(dto.getStatusLog().getStatus());
                log.setUpdatedAt(LocalDateTime.now());
                statusLogRepo.save(log);
            }
            return savedResult;
        }).toList();

        return getSessionDto(session);
    }

    private MatchingSession createNewSession(User user) {
        MatchingSession session = new MatchingSession();
        session.setUser(user);
        session.setCreatedAt(LocalDateTime.now());
        return session;
    }

    public List<MatchingSessionDto> getUserSessions(Long userId) {
        return sessionRepo.findByUserId(userId).stream()
                .map(this::getSessionDto)
                .collect(Collectors.toList());
    }

    public List<MatchingResultDto> getResultsBySession(Long sessionId) {
        return resultRepo.findBySessionId(sessionId).stream()
                .map(this::getResultDto)
                .collect(Collectors.toList());
    }

    public List<MatchingScoreDto> getScoresByResult(Long resultId) {
        return scoreRepo.findByResultId(resultId).stream().map(s -> {
            MatchingScoreDto dto = new MatchingScoreDto();
            dto.setCriteria(s.getCriteria());
            dto.setScore(s.getScore());
            dto.setReason(s.getReason());
            return dto;
        }).collect(Collectors.toList());
    }

    public MatchingStatusLogDto getStatusLog(Long resultId) {
        Optional<MatchingStatusLog> log = statusLogRepo.findByResultId(resultId);
        return log.map(l -> {
            MatchingStatusLogDto dto = new MatchingStatusLogDto();
            dto.setStatus(l.getStatus());
            dto.setUpdatedAt(l.getUpdatedAt());
            return dto;
        }).orElse(null);
    }

    @Transactional
    public void updateStatus(Long resultId, String status) {
        MatchingStatusLog log = statusLogRepo.findByResultId(resultId).orElse(new MatchingStatusLog());
        MatchingResult result = resultRepo.findById(resultId)
                .orElseThrow(() -> new RuntimeException("Result not found"));

        log.setResult(result);
        log.setStatus(status);
        log.setUpdatedAt(LocalDateTime.now());
        statusLogRepo.save(log);
    }

    private MatchingSessionDto getSessionDto(MatchingSession session) {
        MatchingSessionDto dto = new MatchingSessionDto();
        dto.setId(session.getId());
        dto.setUserId(session.getUser().getId());
        dto.setCreatedAt(session.getCreatedAt());
        dto.setResults(getResultsBySession(session.getId()));
        return dto;
    }

    private MatchingResultDto getResultDto(MatchingResult result) {
        MatchingResultDto dto = new MatchingResultDto();
        dto.setId(result.getId());
        dto.setSessionId(result.getSession().getId());
        dto.setScholarshipId(result.getScholarshipId());
        dto.setType(result.getType());
        dto.setTotalScore(result.getTotalScore());
        dto.setScores(getScoresByResult(result.getId()));
        dto.setStatusLog(getStatusLog(result.getId()));
        return dto;
    }
}