-- V3.11__create_scoring_weights.sql

-- 1. Tạo bảng scoring_weights
CREATE TABLE IF NOT EXISTS scoring_weights (
                                               name VARCHAR(64)     NOT NULL,
    weight DOUBLE        NOT NULL,
    PRIMARY KEY (name)
    ) ENGINE=InnoDB
    DEFAULT CHARSET=utf8mb4
    COLLATE=utf8mb4_unicode_ci;

-- 2. Seed các trọng số mặc định
INSERT INTO scoring_weights (name, weight) VALUES
                                               ('GPA',                 0.40),
                                               ('LANGUAGE',            0.20),
                                               ('MAJOR',               0.20),
                                               ('COUNTRY',             0.10),
                                               ('SCHOLARSHIP_SUPPORT', 0.10)
    ON DUPLICATE KEY UPDATE
                         weight = VALUES(weight);
