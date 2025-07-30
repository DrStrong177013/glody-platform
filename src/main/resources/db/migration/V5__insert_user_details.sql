-- V5__insert_user_details.sql

-- === User Profiles ===
INSERT IGNORE INTO user_profiles (
  id,
  user_id,
  full_name,
  date_of_birth,
  gender,
  phone_number,
  address,
  country_id,
  university_id,
  enrollment_year,
  major,
  -- cột mới, nếu có
  avatar_url,
  education_level,
  extracurricular_activities,
  gpa,
  gpa_scale,
  nationality,
  second_language_certificate,
  target_country,
  target_semester,
  target_year,
  university_name,
  created_at,
  updated_at,
  is_active
) VALUES
  (1, 1, 'Alice Nguyễn', '1998-05-12', 'female', '+84 912 345 678', '123 Lê Lợi, Q1, TP.HCM, Vietnam', 5, 5, 2016, 'Computer Science',
   NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,NULL,NULL,
   NOW(), NOW(), TRUE),
  (2, 2, 'Bob Trần',      '1985-03-20', 'male',   '+84 913 222 333', '456 Nguyễn Huệ, Q1, TP.HCM, Vietnam', 5, 3, 2010, 'International Education',
   NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,NULL,NULL,
   NOW(), NOW(), TRUE),
  (3, 3, 'Glodyai',       '1990-07-15', 'male',   '+84 914 444 555', '789 Trần Phú, Hà Nội, Vietnam',            5, 4, 2008, 'Information Systems',
   NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,NULL,NULL,
   NOW(), NOW(), TRUE),
  (4, 4, 'Diana Lê',      '1995-11-30', 'female', '+84 915 666 777', '101 Nguyễn Trãi, Hà Nội, Vietnam',         2, 2, 2014, 'Management',
   NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,NULL,NULL,
   NOW(), NOW(), TRUE),
  (5, 5, 'Edward King',   '1997-02-22', 'male',   '+84 916 888 999', '202 Phạm Ngũ Lão, Đà Nẵng, Vietnam',       3, 1, 2015, 'Engineering',
   NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,NULL,NULL,
   NOW(), NOW(), TRUE);

-- === Language Certificates ===
INSERT IGNORE INTO language_certificates (
  id,
  profile_id,
  certificate_name,   -- thay cho type
  result_level,       -- cột mới
  score,
  skill,              -- cột mới
  created_at,
  updated_at,
  is_active
) VALUES
  (1, 1, 'IELTS',  '7.0',  7.0, NULL, NOW(), NOW(), TRUE),
  (2, 1, 'TOEFL', '95',   95.0, NULL, NOW(), NOW(), TRUE),
  (3, 2, 'TOEFL', '100', 100.0, NULL, NOW(), NOW(), TRUE),
  (4, 4, 'IELTS',  '6.5',  6.5, NULL, NOW(), NOW(), TRUE),
  (5, 5, 'TOEFL', '90',   90.0, NULL, NOW(), NOW(), TRUE),
  (6, 5, 'IELTS',  '6.0',  6.0, NULL, NOW(), NOW(), TRUE);

-- === Expert Profiles ===
INSERT IGNORE INTO expert_profiles (
  id,
  user_id,
  bio,
  years_of_experience,  -- đổi tên từ years_experience
  avatar_url,           -- cột mới
  experience,           -- cột mới
  expertise,            -- cột mới
  created_at,
  updated_at,
  is_active
) VALUES
  (1, 2,
   'Over 12 years of experience advising students on study-abroad programs in Australia and Canada.',
   12, NULL, NULL, NULL,
   NOW(), NOW(), TRUE);

-- === Expert Countries (unchanged) ===
INSERT IGNORE INTO expert_countries (
  id,
  expert_profile_id,
  country_id,
  created_at,
  updated_at,
  is_active
) VALUES
  (1, 1, 1, NOW(), NOW(), TRUE),
  (2, 1, 3, NOW(), NOW(), TRUE);

-- === User Roles (unchanged) ===
INSERT IGNORE INTO user_roles (
  id,
  user_id,
  role_id,
  created_at,
  updated_at,
  is_active
) VALUES
  (1, 1, 1, NOW(), NOW(), TRUE),
  (2, 2, 2, NOW(), NOW(), TRUE),
  (3, 3, 3, NOW(), NOW(), TRUE),
  (4, 4, 1, NOW(), NOW(), TRUE),
  (5, 5, 1, NOW(), NOW(), TRUE);

-- === User Subscriptions (unchanged) ===
INSERT IGNORE INTO user_subscriptions (
  id,
  user_id,
  package_id,
  start_date,
  end_date,
  created_at,
  updated_at,
  is_active
) VALUES
  (1, 1, 1, '2025-07-10', '2025-07-17', NOW(), NOW(), TRUE),
  (2, 2, 2, '2025-07-01', '2025-07-31', NOW(), NOW(), TRUE),
  (3, 3, 4, '2025-06-01', '2026-05-31', NOW(), NOW(), TRUE),
  (4, 4, 3, '2025-05-01', '2025-10-28', NOW(), NOW(), TRUE),
  (5, 5, 2, '2025-07-01', '2025-07-31', NOW(), NOW(), TRUE);
