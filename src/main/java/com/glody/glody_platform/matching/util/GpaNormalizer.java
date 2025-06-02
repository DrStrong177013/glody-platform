package com.glody.glody_platform.matching.util;

import com.glody.glody_platform.users.entity.UserProfile;

public class GpaNormalizer {

    /**
     * Trả về GPA chuẩn hóa theo thang 10.0
     * @param profile hồ sơ người dùng
     * @return GPA thang 10.0
     */
    public static double normalize(UserProfile profile) {
        if (profile.getGpa() == null) return 0.0;

        Double gpa = profile.getGpa();
        Double scale = profile.getGpaScale(); // optional field trong UserProfile

        if (scale != null && scale > 0) {
            return (gpa / scale) * 10.0;
        }

        // fallback: đoán thang GPA
        return guessAndNormalize(gpa);
    }

    /**
     * Đoán thang GPA nếu người dùng không khai báo rõ
     * @param rawGpa GPA gốc
     * @return GPA thang 10.0
     */
    public static double guessAndNormalize(double rawGpa) {
        if (rawGpa <= 4.0) {
            return (rawGpa / 4.0) * 10.0;
        } else if (rawGpa <= 10.0) {
            return rawGpa; // đã ở thang 10
        } else if (rawGpa <= 100.0) {
            return rawGpa / 10.0; // thang 100 → 10
        }

        throw new IllegalArgumentException("Không thể xác định thang GPA cho giá trị: " + rawGpa);
    }
}
