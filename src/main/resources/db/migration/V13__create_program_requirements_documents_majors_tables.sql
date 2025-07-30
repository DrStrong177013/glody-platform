-- V13__create_program_requirements_documents_majors_tables.sql

CREATE TABLE program_requirements (
                                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      created_at DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                      updated_at DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                      deleted_at DATETIME    NULL,
                                      is_deleted BIT         NOT NULL DEFAULT 0,
                                      deadline VARCHAR(255),
                                      gpa_requirement VARCHAR(255),
                                      language_requirement VARCHAR(255),
                                      program_id BIGINT      NOT NULL UNIQUE,
                                      CONSTRAINT fk_pr_program FOREIGN KEY (program_id) REFERENCES programs(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE program_documents (
                                   requirement_id BIGINT  NOT NULL,
                                   document_name VARCHAR(255) NOT NULL,
                                   CONSTRAINT fk_pd_requirement FOREIGN KEY (requirement_id) REFERENCES program_requirements(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE program_majors (
                                program_id BIGINT      NOT NULL,
                                major VARCHAR(255)     NOT NULL,
                                CONSTRAINT fk_pm_program FOREIGN KEY (program_id) REFERENCES programs(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
