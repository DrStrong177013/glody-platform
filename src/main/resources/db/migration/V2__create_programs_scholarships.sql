-- V2__create_programs_scholarships.sql

-- 1. Schools
CREATE TABLE schools (
                         id               BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
                         created_at       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         deleted_at       DATETIME     NULL,
                         is_deleted       BIT          NOT NULL DEFAULT b'0',
                         updated_at       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         name             VARCHAR(255) NOT NULL,
                         name_en          VARCHAR(255),
                         established_year INT,
                         introduction     TEXT,
                         location         VARCHAR(255),

    -- Thêm trường country_id để liên kết với bảng countries
                         country_id       BIGINT       NOT NULL,

                         rank_text        VARCHAR(255),
                         website          VARCHAR(255),
                         logo_url         VARCHAR(255),

    -- Tạo index và ràng buộc khóa ngoại
                         INDEX idx_schools_country_id (country_id),
                         CONSTRAINT fk_schools_country
                             FOREIGN KEY (country_id)
                                 REFERENCES countries(id)
                                 ON UPDATE CASCADE
                                 ON DELETE RESTRICT
) ENGINE=InnoDB;

-- 2. Scholarships
CREATE TABLE scholarships (
                              id                   BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
                              created_at           DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              deleted_at           DATETIME     NULL,
                              is_deleted           BIT          NOT NULL DEFAULT b'0',
                              updated_at           DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                              title                VARCHAR(255) NOT NULL,
                              sponsor              VARCHAR(255),
                              value                VARCHAR(255),
                              description          TEXT,
                              application_deadline DATE,
                              school_id            BIGINT       NOT NULL,
                              FOREIGN KEY (school_id) REFERENCES schools(id)
) ENGINE=InnoDB;

-- 3. Scholarship Conditions (1-N từ scholarships)
CREATE TABLE scholarship_conditions (
                                        scholarship_id BIGINT       NOT NULL,
                                        condition_text VARCHAR(255) NOT NULL,
                                        PRIMARY KEY (scholarship_id, condition_text),
                                        FOREIGN KEY (scholarship_id) REFERENCES scholarships(id)
) ENGINE=InnoDB;

-- 4. Programs
CREATE TABLE programs (
                          id                  BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
                          created_at          DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          deleted_at          DATETIME     NULL,
                          is_deleted          BIT          NOT NULL DEFAULT b'0',
                          updated_at          DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          dorm_fee            VARCHAR(255),
                          tuition_fee         VARCHAR(255),
                          living_cost         VARCHAR(255),
                          scholarship_support BIT          NOT NULL DEFAULT b'0',
                          language            VARCHAR(255),
                          level               VARCHAR(255),
                          school_id           BIGINT       NOT NULL,
                          FOREIGN KEY (school_id) REFERENCES schools(id)
) ENGINE=InnoDB;

-- 5. Program Requirements (1-1 với programs)
CREATE TABLE program_requirements (
                                      id                   BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                      created_at           DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                      deleted_at           DATETIME     NULL,
                                      is_deleted           BIT          NOT NULL DEFAULT b'0',
                                      updated_at           DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                      deadline             VARCHAR(255),
                                      gpa_requirement      VARCHAR(255),
                                      language_requirement VARCHAR(255),
                                      program_id           BIGINT       NOT NULL UNIQUE,
                                      FOREIGN KEY (program_id) REFERENCES programs(id)
) ENGINE=InnoDB;

-- 6. Program Majors (N-1 tới programs)
CREATE TABLE program_majors (
                                program_id BIGINT       NOT NULL,
                                major      VARCHAR(255) NOT NULL,
                                PRIMARY KEY (program_id, major),
                                FOREIGN KEY (program_id) REFERENCES programs(id)
) ENGINE=InnoDB;

-- 7. Program Documents (N-1 tới program_requirements)
CREATE TABLE program_documents (
                                   requirement_id BIGINT       NOT NULL,
                                   document_name  VARCHAR(255) NOT NULL,
                                   PRIMARY KEY (requirement_id, document_name),
                                   FOREIGN KEY (requirement_id) REFERENCES program_requirements(id)
) ENGINE=InnoDB;
