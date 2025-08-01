-- V3_03_profiles_and_experts.sql

-- 1) USER_PROFILES (6 bản ghi)
INSERT IGNORE INTO user_profiles
  (id, created_at, deleted_at, is_deleted, updated_at,
   avatar_url, date_of_birth, education_level, extracurricular_activities,
   full_name, gpa, gpa_scale, major, nationality,
   second_language_certificate, target_country,
   target_semester, target_year, university_name, user_id)
VALUES
  (1, NOW(), NULL, b'0', NOW(), NULL,'1980-01-01','Tiến sĩ','Nghiên cứu độc lập',
   'Nguyễn Văn A', 3.9, 4,'Toán học','Việt Nam','IELTS 8.0','Anh','Học kỳ Thu',2025,'ĐHQGHN',1),
  (2, NOW(), NULL, b'0', NOW(), NULL,'1985-03-20','Thạc sĩ','Giảng viên tự do',
   'Bob Chuyên gia',3.7,4,'Quản trị Kinh doanh','Hoa Kỳ','TOEFL 100','Mỹ','Học kỳ Xuân',2026,'MIT',2),
  (3, NOW(), NULL, b'0', NOW(), NULL,'2000-05-15','Tốt nghiệp THPT','Câu lạc bộ Anh ngữ',
   'Nguyễn Thị Alice',8.5,10,'Khoa học Máy tính','Việt Nam','IELTS 7.5','Anh','Học kỳ Thu',2025,'ĐHQGHN',3),
  (4, NOW(), NULL, b'0', NOW(), NULL,'1999-08-08','Tốt nghiệp THPT','Đội tuyển HSG',
   'Lê Thị Diana',8.0,10,'Kinh tế','Việt Nam','TOEFL 95','Mỹ','Học kỳ Xuân',2025,'ĐHQGHN',4),
  (5, NOW(), NULL, b'0', NOW(), NULL,'1998-12-12','Cử nhân','Tình nguyện viên',
   'Trương Edward',3.5,4,'Khoa học Môi trường','Canada','IELTS 7.0','Canada','Học kỳ Thu',2025,'University of Toronto',5),
  (6, NOW(), NULL, b'0', NOW(), NULL,'2001-02-02','Tốt nghiệp THPT','CLB Robot',
   'Phạm Charlie',8.2,10,'Cơ điện tử','Úc','IELTS 7.0','Úc','Học kỳ Xuân',2026,'ANU',6);

-- 2) EXPERT_PROFILES (6 bản ghi)
INSERT IGNORE INTO expert_profiles
  (id, created_at, deleted_at, is_deleted, updated_at,
   avatar_url, bio, years_of_experience, experience, expertise, user_id)
VALUES
  (1, NOW(), NULL, b'0', NOW(), NULL,
   'Chuyên gia Du học lâu năm',10,'Tư vấn toàn cầu','Định cư, Hồ sơ',1),
  (2, NOW(), NULL, b'0', NOW(), NULL,
   'Tư vấn viên hàng đầu USA',8,'Xin VISA, Học bổng','Mỹ, Anh',2),
  (3, NOW(), NULL, b'0', NOW(), NULL,
   'Cố vấn Du học Úc',5,'Visa & hồ sơ','Úc',5),
  (4, NOW(), NULL, b'0', NOW(), NULL,
   'Chuyên gia STEM',6,'STEM Scholarships','Toán, Khoa học',6),
  (5, NOW(), NULL, b'0', NOW(), NULL,
   'Tư vấn Du học Anh',4,'GPA & IELTS','Anh quốc',3),
  (6, NOW(), NULL, b'0', NOW(), NULL,
   'Tư vấn Kinh tế',3,'Chứng chỉ CFA, GMAT','Kinh tế',4);

-- 3) EXPERT_COUNTRIES (6 bản ghi)
INSERT IGNORE INTO expert_countries (expert_id, country_id)
VALUES
  (1,1),(2,3),(3,4),(4,6),(5,2),(6,1);

-- 4) LANGUAGE_CERTIFICATES (6 bản ghi)
INSERT IGNORE INTO language_certificates
  (id, created_at, deleted_at, is_deleted, updated_at,
   certificate_name, result_level, score, skill, profile_id)
VALUES
  (1,NOW(),NULL,b'0',NOW(),'IELTS','Academic',8.0,'Nghe,Nói,Đọc,Viết',1),
  (2,NOW(),NULL,b'0',NOW(),'TOEFL','iBT',100,'RL,LS,SP,WR',2),
  (3,NOW(),NULL,b'0',NOW(),'GRE','General',320,'Verbal,Quant,Analytical',3),
  (4,NOW(),NULL,b'0',NOW(),'GMAT','Overall',700,'Tất cả kỹ năng',4),
  (5,NOW(),NULL,b'0',NOW(),'SAT','Math/Reading',1450,'Toán,Đọc',5),
  (6,NOW(),NULL,b'0',NOW(),'ACT','Composite',30,'Science,Math,Reading,English',6);
