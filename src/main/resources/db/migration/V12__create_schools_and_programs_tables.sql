-- V12__create_schools_and_programs_tables.sql

CREATE TABLE schools (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         created_at DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         updated_at DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         deleted_at DATETIME    NULL,
                         is_deleted BIT         NOT NULL DEFAULT 0,
                         established_year INT,
                         introduction TEXT,
                         location VARCHAR(255),
                         logo_url VARCHAR(255),
                         name VARCHAR(255)      NOT NULL,
                         name_en VARCHAR(255),
                         rank_text VARCHAR(255),
                         website VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE programs (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          created_at DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          updated_at DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          deleted_at DATETIME    NULL,
                          is_deleted BIT         NOT NULL DEFAULT 0,
                          dorm_fee VARCHAR(255),
                          living_cost VARCHAR(255),
                          tuition_fee VARCHAR(255),
                          language ENUM(
    'English',
    'Vietnamese',
    'Spanish',
    'French',
    'German',
    'Chinese',
    'Japanese',
    'Other'
  ) DEFAULT 'Other',
                          level ENUM(
    'Undergraduate',
    'Graduate',
    'Postgraduate',
    'Certificate',
    'Diploma',
    'Language',
    'Other'
  ) DEFAULT 'Other',
                          scholarship_support BIT NOT NULL DEFAULT 0,
                          school_id BIGINT       NOT NULL,
                          CONSTRAINT fk_programs_school FOREIGN KEY (school_id) REFERENCES schools(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
