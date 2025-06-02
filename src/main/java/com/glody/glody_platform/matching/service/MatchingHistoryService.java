package com.glody.glody_platform.matching.service;

import com.glody.glody_platform.common.PageResponse;
import com.glody.glody_platform.matching.dto.MatchingHistoryDto;
import com.glody.glody_platform.matching.dto.ScholarshipMatchDto;
import com.glody.glody_platform.matching.entity.MatchingHistory;
import com.glody.glody_platform.matching.repository.MatchingHistoryRepository;
import com.glody.glody_platform.users.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchingHistoryService {

    private final MatchingHistoryRepository repository;

    public PageResponse<MatchingHistoryDto> getMatchingHistory(
            Long userId,
            String matchType,
            int page,
            int size
    ) {
        List<MatchingHistory> all = repository.findByUserIdAndMatchType(userId, matchType);

        List<MatchingHistoryDto> dtos = all.stream()
                .sorted(Comparator.comparing(MatchingHistory::getCreatedAt).reversed())
                .map(this::toDto)
                .collect(Collectors.toList());

        int total = dtos.size();
        int totalPages = (int) Math.ceil((double) total / size);
        int fromIndex = Math.min(page * size, total);
        int toIndex = Math.min(fromIndex + size, total);

        List<MatchingHistoryDto> paged = dtos.subList(fromIndex, toIndex);

        PageResponse.PageInfo pageInfo = new PageResponse.PageInfo(
                page, size, totalPages, total,
                page < totalPages - 1,
                page > 0
        );

        return new PageResponse<>(paged, pageInfo);
    }

    public void saveScholarshipMatchList(User user, List<ScholarshipMatchDto> dtos) {
        List<MatchingHistory> histories = dtos.stream()
                .map(dto -> {
                    MatchingHistory h = new MatchingHistory();
                    h.setUser(user);
                    h.setReferenceId(dto.getId());
                    h.setMatchType("SCHOLARSHIP");
                    h.setMatchPercentage(dto.getMatchPercentage());
                    h.setAdditionalInfo(dto.getTitle()); // hoặc dto.getSponsor() tuỳ bạn
                    return h;
                })
                .collect(Collectors.toList());

        repository.saveAll(histories);
    }
    private MatchingHistoryDto toDto(MatchingHistory history) {
        return new MatchingHistoryDto(
                history.getId(),
                history.getUser().getId(),
                history.getReferenceId(),
                history.getMatchType(),
                history.getMatchPercentage(),
                history.getAdditionalInfo(),
                history.getCreatedAt()
        );
    }
}
