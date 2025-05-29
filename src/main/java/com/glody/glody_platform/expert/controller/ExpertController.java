package com.glody.glody_platform.expert.controller;

import com.glody.glody_platform.common.PageResponse;
import com.glody.glody_platform.expert.dto.ExpertProfileDto;
import com.glody.glody_platform.expert.service.ExpertService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/experts")
@RequiredArgsConstructor
public class ExpertController {

    private final ExpertService expertService;

//    @GetMapping
//    public Page<ExpertProfileDto> getExperts(
//            @RequestParam(required = false, defaultValue = "") String keyword,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "6") int size) {
//
//        Pageable pageable = PageRequest.of(page, size);
//        return expertService.getExperts(keyword, pageable);
//    }

//    @GetMapping("/countries")
//    public List<String> getAllAdvisingCountries() {
//        return expertService.getAllAdvisingCountries();
//    }
    @GetMapping
    public ResponseEntity<PageResponse<ExpertProfileDto>> searchExperts(
            @RequestParam(required = false) String country,
            @RequestParam(required = false) Integer minYears,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        PageResponse<ExpertProfileDto> result = expertService.searchExperts(
                country, minYears, keyword, page, size, sortBy, direction);
        return ResponseEntity.ok(result);
    }

}
