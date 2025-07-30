-- V17__insert_sample_scholarships_and_conditions.sql

-- Sample Scholarships
INSERT INTO scholarships
(school_id, title, sponsor, value, application_deadline, description)
VALUES
    (
        (SELECT id FROM schools WHERE name = 'Đại học Quốc gia Hà Nội'),
        'Học bổng Tài năng trẻ',
        'VNU Foundation',
        '50% tuition',
        '2025-04-30',
        'Dành cho sinh viên xuất sắc, có thành tích học tập nổi bật.'
    ),
    (
        (SELECT id FROM schools WHERE name = 'University of Oxford'),
        'Rhodes Scholarship',
        'Rhodes Trust',
        'Fully Funded',
        '2025-08-01',
        'One of the oldest and most prestigious scholarships for international students.'
    ),
    (
        (SELECT id FROM schools WHERE name = 'Massachusetts Institute of Technology'),
        'MIT Presidential Fellowship',
        'MIT',
        '$40,000 stipend',
        '2025-11-15',
        'Competitive fellowship for graduate research.'
    );

-- Sample Scholarship Conditions
INSERT INTO scholarship_conditions
(scholarship_id, condition_text)
VALUES
    (
        (SELECT id FROM scholarships WHERE title = 'Học bổng Tài năng trẻ'),
        'GPA tối thiểu 8.0/10'
    ),
    (
        (SELECT id FROM scholarships WHERE title = 'Học bổng Tài năng trẻ'),
        'Hoàn thành bài luận cá nhân'
    ),
    (
        (SELECT id FROM scholarships WHERE title = 'Rhodes Scholarship'),
        'Age below 28'
    ),
    (
        (SELECT id FROM scholarships WHERE title = 'Rhodes Scholarship'),
        'Leadership achievements'
    ),
    (
        (SELECT id FROM scholarships WHERE title = 'MIT Presidential Fellowship'),
        'Must be graduate student'
    ),
    (
        (SELECT id FROM scholarships WHERE title = 'MIT Presidential Fellowship'),
        'Outstanding research proposal'
    );
