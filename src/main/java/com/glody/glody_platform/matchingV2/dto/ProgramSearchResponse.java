package com.glody.glody_platform.matchingV2.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProgramSearchResponse {
    private MatchingSummary summary;
    private List<ProgramDetailDto> programs;
    private List<String> recommendations;
}
