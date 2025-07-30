-- V16__insert_sample_programs_and_related_data.sql

-- Sample Programs
INSERT INTO programs
(school_id, dorm_fee, living_cost, tuition_fee, language, level, scholarship_support)
VALUES
    (
        (SELECT id FROM schools WHERE name = 'Đại học Quốc gia Hà Nội'),
        '200 USD/month',
        '500 USD/month',
        '1500 USD/semester',
        'Vietnamese',
        'Undergraduate',
        1
    ),
    (
        (SELECT id FROM schools WHERE name = 'University of Oxford'),
        '300 GBP/month',
        '800 GBP/month',
        '9000 GBP/year',
        'English',
        'Undergraduate',
        1
    ),
    (
        (SELECT id FROM schools WHERE name = 'Massachusetts Institute of Technology'),
        '800 USD/month',
        '1200 USD/month',
        '55000 USD/year',
        'English',
        'Graduate',
        1
    );

-- Sample Program Requirements
INSERT INTO program_requirements
(program_id, deadline, gpa_requirement, language_requirement)
VALUES
    (
        (SELECT id FROM programs WHERE tuition_fee = '1500 USD/semester'),
        '2025-05-30',
        '3.2/4.0',
        'IELTS 6.0'
    ),
    (
        (SELECT id FROM programs WHERE tuition_fee = '9000 GBP/year'),
        '2025-06-15',
        '2:1 Honors',
        'IELTS 7.0'
    ),
    (
        (SELECT id FROM programs WHERE tuition_fee = '55000 USD/year'),
        '2025-12-01',
        '3.5/4.0',
        'TOEFL 100'
    );

-- Sample Program Documents
INSERT INTO program_documents
(requirement_id, document_name)
VALUES
    ((SELECT id FROM program_requirements WHERE gpa_requirement = '3.2/4.0'), 'Transcript'),
    ((SELECT id FROM program_requirements WHERE gpa_requirement = '3.2/4.0'), 'Personal Statement'),
    ((SELECT id FROM program_requirements WHERE gpa_requirement = '2:1 Honors'), 'Transcript'),
    ((SELECT id FROM program_requirements WHERE gpa_requirement = '2:1 Honors'), 'Reference Letters'),
    ((SELECT id FROM program_requirements WHERE gpa_requirement = '3.5/4.0'), 'GRE Scores'),
    ((SELECT id FROM program_requirements WHERE gpa_requirement = '3.5/4.0'), 'Research Proposal');

-- Sample Program Majors
INSERT INTO program_majors
(program_id, major)
VALUES
    ((SELECT id FROM programs WHERE tuition_fee = '1500 USD/semester'), 'Computer Science'),
    ((SELECT id FROM programs WHERE tuition_fee = '1500 USD/semester'), 'Mathematics'),
    ((SELECT id FROM programs WHERE tuition_fee = '9000 GBP/year'), 'Philosophy'),
    ((SELECT id FROM programs WHERE tuition_fee = '55000 USD/year'), 'Mechanical Engineering'),
    ((SELECT id FROM programs WHERE tuition_fee = '55000 USD/year'), 'Aerospace Engineering');
