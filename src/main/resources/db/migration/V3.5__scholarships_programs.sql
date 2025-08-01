-- V3_05_scholarships_programs.sql

-- 1) SCHOLARSHIPS (6 bản ghi)
INSERT IGNORE INTO scholarships
  (id, created_at, deleted_at, is_deleted, updated_at,
   title, sponsor, value, description, application_deadline, school_id)
VALUES
  (1,NOW(),NULL,b'0',NOW(),
   'Học bổng toàn phần Cambridge','Đại học Cambridge','Toàn phần',
   'Hỗ trợ toàn bộ học phí và sinh hoạt', '2025-03-31',1),
  (2,NOW(),NULL,b'0',NOW(),
   'Học bổng bán phần Oxford','Đại học Oxford','50% học phí',
   'Hỗ trợ một nửa học phí',       '2025-04-15',2),
  (3,NOW(),NULL,b'0',NOW(),
   'Học bổng Stanford','Stanford University','Toàn phần',
   'Full tuition waiver',          '2025-05-01',3),
  (4,NOW(),NULL,b'0',NOW(),
   'Học bổng Harvard','Harvard University','75% học phí',
   'Hỗ trợ 75% học phí',           '2025-06-01',4),
  (5,NOW(),NULL,b'0',NOW(),
   'Học bổng ANU','Australian National University','Toàn phần',
   'Scholarship for research',     '2025-07-15',4),
  (6,NOW(),NULL,b'0',NOW(),
   'Học bổng Toronto','University of Toronto','50% học phí',
   'Scholarship đa dạng ngành',    '2025-08-01',5);

-- 2) SCHOLARSHIP_CONDITIONS (6 bản ghi)
INSERT IGNORE INTO scholarship_conditions
  (scholarship_id, condition_text)
VALUES
  (1,'GPA ≥ 3.5'),
  (1,'IELTS ≥ 7.0'),
  (2,'GPA ≥ 3.2'),
  (2,'TOEFL ≥ 100'),
  (3,'GPA ≥ 3.8'),
  (3,'SAT ≥ 1400');

-- 3) PROGRAMS (6 bản ghi)
INSERT IGNORE INTO programs
  (id, created_at, deleted_at, is_deleted, updated_at,
   dorm_fee, tuition_fee, living_cost, scholarship_support,
   language, level, school_id)
VALUES
  (1,NOW(),NULL,b'0',NOW(),'£2,000/năm','£24,000/năm','£10,000/năm',b'1','ENGLISH','DAI_HOC',1),
  (2,NOW(),NULL,b'0',NOW(),'£1,800/năm','£22,000/năm','£9,500/năm', b'1','ENGLISH','CAO_HOC',2),
  (3,NOW(),NULL,b'0',NOW(),'$2,500/năm','$25,000/năm','$11,000/năm', b'1','ENGLISH','DAI_HOC',3),
  (4,NOW(),NULL,b'0',NOW(),'AU$2,000/năm','AU$23,000/năm','AU$9,000/năm',b'1','ENGLISH','CAO_HOC',4),
  (5,NOW(),NULL,b'0',NOW(),'$3,000/năm','$30,000/năm','$12,000/năm', b'1','ENGLISH','DAI_HOC',5),
  (6,NOW(),NULL,b'0',NOW(),'CA$2,200/năm','CA$26,000/năm','CA$10,500/năm',b'1','ENGLISH','CAO_HOC',6);

-- 4) PROGRAM_REQUIREMENTS (6 bản ghi)
INSERT IGNORE INTO program_requirements
  (id, created_at, deleted_at, is_deleted, updated_at,
   deadline, gpa_requirement, language_requirement, program_id)
VALUES
  (1,NOW(),NULL,b'0',NOW(),'30-04-2025','3.0/4.0','IELTS 6.5',1),
  (2,NOW(),NULL,b'0',NOW(),'15-05-2025','3.2/4.0','IELTS 7.0',2),
  (3,NOW(),NULL,b'0',NOW(),'30-06-2025','3.5/4.0','TOEFL 100',3),
  (4,NOW(),NULL,b'0',NOW(),'31-07-2025','3.3/4.0','IELTS 6.5',4),
  (5,NOW(),NULL,b'0',NOW(),'15-08-2025','3.7/4.0','SAT 1400',5),
  (6,NOW(),NULL,b'0',NOW(),'01-09-2025','3.1/4.0','ACT 30',6);
