package com.glody.glody_platform.matching.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequirementParser {

    /**
     * Parse GPA yêu cầu từ chuỗi, ví dụ: "GPA >= 3.0/4" => 3.0
     */
    public static double extractGpa(String gpaRequirement) {
        if (gpaRequirement == null || gpaRequirement.isBlank()) {
            throw new IllegalArgumentException("Chuỗi yêu cầu GPA bị rỗng");
        }

        Pattern pattern = Pattern.compile("(\\d+(\\.\\d+)?)");
        Matcher matcher = pattern.matcher(gpaRequirement);
        if (matcher.find()) {
            return Double.parseDouble(matcher.group(1));
        }
        throw new NumberFormatException("Không thể parse GPA từ: " + gpaRequirement);
    }

    /**
     * Tách loại chứng chỉ tiếng Anh từ chuỗi yêu cầu
     */
    public static String extractRequiredLanguageType(String requirement) {
        if (requirement == null) return "UNKNOWN";

        String upper = requirement.toUpperCase();
        if (upper.contains("IELTS")) return "IELTS";
        if (upper.contains("TOEFL")) return "TOEFL";
        if (upper.contains("TOEIC")) return "TOEIC";
        if (upper.contains("DUOLINGO")) return "DUOLINGO";
        return "UNKNOWN";
    }

    /**
     * Tách điểm chứng chỉ yêu cầu từ text, ví dụ: "IELTS >= 6.5" => 6.5
     */
    public static double extractRequiredLanguageScore(String requirement) {
        if (requirement == null || requirement.isBlank()) {
            throw new IllegalArgumentException("Chuỗi yêu cầu chứng chỉ rỗng");
        }

        Pattern pattern = Pattern.compile("(\\d+(\\.\\d+)?)");
        Matcher matcher = pattern.matcher(requirement);
        if (matcher.find()) {
            return Double.parseDouble(matcher.group(1));
        }

        throw new NumberFormatException("Không thể parse điểm chứng chỉ từ: " + requirement);
    }
}
