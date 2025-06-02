package com.glody.glody_platform.matching.matcher;

import com.glody.glody_platform.university.entity.Program;
import com.glody.glody_platform.university.entity.ProgramRequirement;
import com.glody.glody_platform.users.entity.UserProfile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GpaMatcherTest {

    private final GpaMatcher matcher = new GpaMatcher();

    @Test
    void shouldPassWhenUserGpaMeetsRequirement() {
        UserProfile profile = new UserProfile();
        profile.setGpa(3.5);

        Program program = new Program();
        ProgramRequirement req = new ProgramRequirement();
        req.setGpaRequirement("3.0/4");
        program.setRequirement(req);

        MatchResult result = matcher.evaluate(profile, program);

        assertTrue(result.isMatched());
        assertEquals("Đạt GPA yêu cầu.", result.getReason());
    }

    @Test
    void shouldFailWhenUserGpaTooLow() {
        UserProfile profile = new UserProfile();
        profile.setGpa(2.5);

        Program program = new Program();
        ProgramRequirement req = new ProgramRequirement();
        req.setGpaRequirement("3.0/4");
        program.setRequirement(req);

        MatchResult result = matcher.evaluate(profile, program);

        assertFalse(result.isMatched());
        assertEquals("GPA không đủ yêu cầu.", result.getReason());
    }

    @Test
    void shouldFailWhenInvalidRequirement() {
        UserProfile profile = new UserProfile();
        profile.setGpa(3.5);

        Program program = new Program();
        ProgramRequirement req = new ProgramRequirement();
        req.setGpaRequirement("không hợp lệ");
        program.setRequirement(req);

        MatchResult result = matcher.evaluate(profile, program);

        assertFalse(result.isMatched());
        assertEquals("Không thể phân tích GPA yêu cầu.", result.getReason());
    }
}
