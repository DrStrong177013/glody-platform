package com.glody.glody_platform.matchingV2.service;

import com.glody.glody_platform.matchingV2.dto.SchoolLightDto;
import com.glody.glody_platform.matchingV2.criteria.SchoolSearchCriteria;
import com.glody.glody_platform.university.entity.School;
import com.glody.glody_platform.users.dto.UserProfileDto;
import com.glody.glody_platform.users.repository.UserProfileRepository;
import com.glody.glody_platform.university.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolMatchingService {
    private final UserProfileRepository userProfileRepository;
    private final SchoolRepository schoolRepository;

    public List<SchoolLightDto> matchSchools(Long userId, SchoolSearchCriteria criteria) {
        // 1. Lấy hồ sơ user
        UserProfileDto profile = userProfileRepository.findDtoByUserId(userId);

        // 2. Lấy danh sách trường (ở đây lấy all, có thể thêm filter bằng repository nếu cần)
        List<School> schools = schoolRepository.findAll();

        List<SchoolLightDto> result = new ArrayList<>();
        for (School school : schools) {
            double match = calcMatch(profile, school, criteria);
            // Nếu cần filter theo minMatchPercentage, có thể bỏ qua trường có match < x%

            SchoolLightDto dto = new SchoolLightDto();
            dto.setId(school.getId());
            dto.setName(school.getName());
            dto.setLogoUrl(school.getLogoUrl());
            dto.setCountry(school.getCountry() != null ? school.getCountry().getName() : null);
            dto.setMatchPercentage(match);
            result.add(dto);
        }
        return result;
    }

    // Logic tính match đủ các tiêu chí
    private double calcMatch(UserProfileDto profile, School school, SchoolSearchCriteria criteria) {
        int score = 0;
        int total = 0;

        // 1. Quốc gia trường vs targetCountry (profile hoặc criteria)
        String userCountry = (criteria.getTargetCountry() != null && !criteria.getTargetCountry().isEmpty())
                ? criteria.getTargetCountry()
                : profile.getTargetCountry();
        String schoolCountry = school.getCountry() != null ? school.getCountry().getName() : null;
        total++;
        if (schoolCountry != null && userCountry != null && schoolCountry.equalsIgnoreCase(userCountry)) score++;

        // 2. Ngành học (nếu trường có list ngành, bạn custom lại)
        String userMajor = (criteria.getMajor() != null && !criteria.getMajor().isEmpty())
                ? criteria.getMajor()
                : profile.getMajor();
        total++;
        // TODO: Nếu trường có list ngành thì check trùng, tạm cho luôn điểm (để code chạy):
        score++; // => bạn sửa lại đúng logic check list ngành sau

        // 3. Ranking (nếu trường có trường ranking và criteria)
        if (criteria.getRanking() != null && school.getRankText() != null) {
            total++;
            if (school.getRankText().toLowerCase().contains(criteria.getRanking().toLowerCase())) score++;
        }

        // 4. GPA (nếu user yêu cầu minGpa, trường có thể không có field này)
        if (criteria.getMinGpa() != null && profile.getGpa() != null) {
            total++;
            if (profile.getGpa() >= criteria.getMinGpa()) score++;
        }

        // Có thể bổ sung thêm các tiêu chí khác: location, loại trường...

        return total > 0 ? (score * 100.0 / total) : 0.0;
    }
}
