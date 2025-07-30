-- V14__create_scholarships_and_conditions_tables.sql

CREATE TABLE scholarships (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              created_at DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              updated_at DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                              deleted_at DATETIME    NULL,
                              is_deleted BIT         NOT NULL DEFAULT 0,
                              title VARCHAR(255)     NOT NULL,
                              sponsor VARCHAR(255),
                              value VARCHAR(255),
                              application_deadline DATE,
                              description TEXT,
                              school_id BIGINT       NOT NULL,
                              CONSTRAINT fk_scholarships_school FOREIGN KEY (school_id) REFERENCES schools(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE scholarship_conditions (
                                        scholarship_id BIGINT  NOT NULL,
                                        condition_text VARCHAR(255) NOT NULL,
                                        CONSTRAINT fk_sc_scholarship FOREIGN KEY (scholarship_id) REFERENCES scholarships(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
