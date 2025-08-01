package com.glody.glody_platform.matchingV2.specification;

import com.glody.glody_platform.catalog.entity.Country;
import com.glody.glody_platform.matchingV2.criteria.ProgramSearchCriteria;
import com.glody.glody_platform.university.entity.Program;
import com.glody.glody_platform.university.entity.ProgramRequirement;
import com.glody.glody_platform.university.entity.School;
import com.glody.glody_platform.users.entity.UserProfile;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProgramSpecification {

    public static Specification<Program> matches(
            ProgramSearchCriteria criteria,
            UserProfile profile
    ) {
        return (root, query, cb) -> {
            List<Predicate> preds = new ArrayList<>();

            // 1) Join đến ProgramRequirement (chỉ để lọc language)
            Join<Program, ProgramRequirement> req = root.join("requirement", JoinType.LEFT);

            // 2) Major filter
            if (criteria.getMajor() != null) {
                Join<Program, ?> majors = root.join("majors", JoinType.LEFT);
                preds.add(cb.equal(
                        cb.lower(majors.get("major")),
                        criteria.getMajor().toLowerCase()
                ));
            }

            // 3) Language filter
            if (criteria.getLanguage() != null) {
                preds.add(cb.equal(
                        cb.lower(req.get("languageRequirement")),
                        criteria.getLanguage().toLowerCase()
                ));
            }

            // 4) Scholarship support
            if (Boolean.TRUE.equals(criteria.getScholarshipSupport())) {
                preds.add(cb.isTrue(root.get("scholarshipSupport")));
            }

            // 5) Country filter: match country.code OR country.name
            if (criteria.getTargetCountry() != null) {
                // join đến School rồi đến Country
                Join<Program, School> schoolJoin =
                        root.join("school", JoinType.LEFT);
                Join<School, Country> countryJoin =
                        schoolJoin.join("country", JoinType.LEFT);

                String target = criteria.getTargetCountry().toLowerCase();

                // predicate cho code = target
                Predicate codeMatch = cb.equal(
                        cb.lower(countryJoin.get("code")),
                        target
                );
                // predicate cho name chứa target (hoặc bạn có thể dùng equal nếu muốn so khớp tuyệt đối)
                Predicate nameMatch = cb.like(
                        cb.lower(countryJoin.get("name")),
                        "%" + target + "%"
                );

                // ghép OR: đúng 1 trong 2
                preds.add(cb.or(codeMatch, nameMatch));
            }

            return cb.and(preds.toArray(new Predicate[0]));
        };
    }
}
