-- V1__create_schema.sql
-- Baseline schema theo ERD

-- 1. Lookup tables
CREATE TABLE countries (
                           id            BIGINT       NOT NULL AUTO_INCREMENT,
                           created_at    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           deleted_at    DATETIME     NULL,
                           is_deleted    BIT          NOT NULL DEFAULT b'0',
                           updated_at    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                           code          VARCHAR(10)  NOT NULL,
                           name          VARCHAR(100) NOT NULL,
                           PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE categories (
                            id            BIGINT       NOT NULL AUTO_INCREMENT,
                            created_at    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            deleted_at    DATETIME     NULL,
                            is_deleted    BIT          NOT NULL DEFAULT b'0',
                            updated_at    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                            name          VARCHAR(255) NOT NULL,
                            slug          VARCHAR(255) NOT NULL,
                            PRIMARY KEY (id),
                            UNIQUE KEY uq_categories_name (name),
                            UNIQUE KEY uq_categories_slug (slug)
) ENGINE=InnoDB;

CREATE TABLE tags (
                      id            BIGINT       NOT NULL AUTO_INCREMENT,
                      created_at    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
                      deleted_at    DATETIME     NULL,
                      is_deleted    BIT          NOT NULL DEFAULT b'0',
                      updated_at    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                      name          VARCHAR(255) NOT NULL,
                      PRIMARY KEY (id),
                      UNIQUE KEY uq_tags_name (name)
) ENGINE=InnoDB;

CREATE TABLE roles (
                       id            BIGINT       NOT NULL AUTO_INCREMENT,
                       created_at    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       deleted_at    DATETIME     NULL,
                       is_deleted    BIT          NOT NULL DEFAULT b'0',
                       updated_at    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       role_name     VARCHAR(255) NOT NULL,
                       PRIMARY KEY (id),
                       UNIQUE KEY uq_roles_role_name (role_name)
) ENGINE=InnoDB;

CREATE TABLE subscription_packages (
                                       id              BIGINT       NOT NULL AUTO_INCREMENT,
                                       created_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                       deleted_at      DATETIME     NULL,
                                       is_deleted      BIT          NOT NULL DEFAULT b'0',
                                       updated_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                       description     VARCHAR(255),
                                       duration_days   INT          NOT NULL,
                                       features        VARCHAR(255),
                                       name            VARCHAR(255) NOT NULL,
                                       price           DOUBLE       NOT NULL,
                                       PRIMARY KEY (id)
) ENGINE=InnoDB;

-- 2. Core user table
CREATE TABLE users (
                       id             BIGINT       NOT NULL AUTO_INCREMENT,
                       created_at     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       deleted_at     DATETIME     NULL,
                       is_deleted     BIT          NOT NULL DEFAULT b'0',
                       updated_at     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       avatar_url     VARCHAR(255),
                       email          VARCHAR(255) NOT NULL,
                       full_name      VARCHAR(255),
                       password_hash  VARCHAR(255) NOT NULL,
                       phone          VARCHAR(255),
                       status         BIT          NOT NULL DEFAULT b'1',
                       reset_password_token VARCHAR(255),
                       reset_password_expiry DATETIME,

                       PRIMARY KEY (id),
                       UNIQUE KEY uq_users_email (email)
) ENGINE=InnoDB;


-- 3. Reference/master data
CREATE TABLE universities (
                              id            BIGINT       NOT NULL AUTO_INCREMENT,
                              created_at    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              deleted_at    DATETIME     NULL,
                              is_deleted    BIT          NOT NULL DEFAULT b'0',
                              updated_at    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                              name          VARCHAR(255) NOT NULL,
                              country_id    BIGINT       NOT NULL,
                              PRIMARY KEY (id),
                              FOREIGN KEY (country_id) REFERENCES countries(id)
) ENGINE=InnoDB;

-- 4. Extended profiles
CREATE TABLE expert_profiles (
                                 id                  BIGINT       NOT NULL AUTO_INCREMENT,
                                 created_at          DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                 deleted_at          DATETIME     NULL,
                                 is_deleted          BIT          NOT NULL DEFAULT b'0',
                                 updated_at          DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                 avatar_url          VARCHAR(255),
                                 bio                 VARCHAR(255),
                                 experience          VARCHAR(255),
                                 expertise           VARCHAR(255),
                                 years_of_experience INT,
                                 user_id             BIGINT       NOT NULL,
                                 PRIMARY KEY (id),
                                 UNIQUE KEY uq_exp_profiles_user (user_id),
                                 FOREIGN KEY (user_id) REFERENCES users(id)
) ENGINE=InnoDB;

CREATE TABLE user_profiles (
                               id                           BIGINT       NOT NULL AUTO_INCREMENT,
                               created_at                   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               deleted_at                   DATETIME     NULL,
                               is_deleted                   BIT          NOT NULL DEFAULT b'0',
                               updated_at                   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                               avatar_url                   VARCHAR(255),
                               date_of_birth                DATE,
                               education_level              VARCHAR(255),
                               extracurricular_activities   VARCHAR(255),
                               full_name                    VARCHAR(255),
                               gpa                          DOUBLE,
                               gpa_scale                    DOUBLE,
                               major                        VARCHAR(255),
                               nationality                  VARCHAR(255),
                               second_language_certificate  VARCHAR(255),
                               target_country               VARCHAR(255),
                               target_semester              VARCHAR(255),
                               target_year                  INT,
                               university_name              VARCHAR(255),
                               user_id                      BIGINT       NOT NULL,
                               PRIMARY KEY (id),
                               UNIQUE KEY uq_usr_profiles_user (user_id),
                               FOREIGN KEY (user_id) REFERENCES users(id)
) ENGINE=InnoDB;

CREATE TABLE language_certificates (
                                       id               BIGINT       NOT NULL AUTO_INCREMENT,
                                       created_at       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                       deleted_at       DATETIME     NULL,
                                       is_deleted       BIT          NOT NULL DEFAULT b'0',
                                       updated_at       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                       certificate_name VARCHAR(255),
                                       result_level     VARCHAR(255),
                                       score            DOUBLE,
                                       skill            VARCHAR(255),
                                       profile_id       BIGINT       NOT NULL,
                                       PRIMARY KEY (id),
                                       FOREIGN KEY (profile_id) REFERENCES user_profiles(id)
) ENGINE=InnoDB;

-- 5. Content: posts / comments / tags
CREATE TABLE posts (
                       id            BIGINT       NOT NULL AUTO_INCREMENT,
                       created_at    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       deleted_at    DATETIME     NULL,
                       is_deleted    BIT          NOT NULL DEFAULT b'0',
                       updated_at    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       content       LONGTEXT,
                       excerpt       TEXT,
                       publish_date  DATETIME,
                       published     BIT          NOT NULL DEFAULT b'0',
                       slug          VARCHAR(255) NOT NULL,
                       thumbnail_url VARCHAR(255),
                       title         VARCHAR(255) NOT NULL,
                       view_count    INT          NOT NULL DEFAULT 0,
                       category_id   BIGINT       NOT NULL,
                       country_id    BIGINT       NOT NULL,
                       PRIMARY KEY (id),
                       UNIQUE KEY uq_posts_slug (slug),
                       FOREIGN KEY (category_id) REFERENCES categories(id),
                       FOREIGN KEY (country_id)   REFERENCES countries(id)
) ENGINE=InnoDB;

CREATE TABLE comments (
                          id            BIGINT       NOT NULL AUTO_INCREMENT,
                          created_at    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          deleted_at    DATETIME     NULL,
                          is_deleted    BIT          NOT NULL DEFAULT b'0',
                          updated_at    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          content       TEXT         NOT NULL,
                          parent_id     BIGINT       NULL,
                          post_id       BIGINT       NOT NULL,
                          user_id       BIGINT       NOT NULL,
                          PRIMARY KEY (id),
                          FOREIGN KEY (parent_id) REFERENCES comments(id),
                          FOREIGN KEY (post_id)   REFERENCES posts(id),
                          FOREIGN KEY (user_id)   REFERENCES users(id)
) ENGINE=InnoDB;

CREATE TABLE post_tags (
                           post_id BIGINT NOT NULL,
                           tag_id  BIGINT NOT NULL,
                           PRIMARY KEY (post_id, tag_id),
                           FOREIGN KEY (post_id) REFERENCES posts(id),
                           FOREIGN KEY (tag_id)  REFERENCES tags(id)
) ENGINE=InnoDB;

-- 6. Mapping & RBAC
CREATE TABLE expert_countries (
                                  expert_id  BIGINT NOT NULL,
                                  country_id BIGINT NOT NULL,
                                  PRIMARY KEY (expert_id, country_id),
                                  FOREIGN KEY (expert_id)  REFERENCES expert_profiles(id),
                                  FOREIGN KEY (country_id)  REFERENCES countries(id)
) ENGINE=InnoDB;

CREATE TABLE user_roles (
                            user_id BIGINT NOT NULL,
                            role_id BIGINT NOT NULL,
                            PRIMARY KEY (user_id, role_id),
                            FOREIGN KEY (user_id) REFERENCES users(id),
                            FOREIGN KEY (role_id) REFERENCES roles(id)
) ENGINE=InnoDB;

-- 7. Matching & consultations
CREATE TABLE matching_histories (
                                    id               BIGINT       NOT NULL AUTO_INCREMENT,
                                    created_at       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                    deleted_at       DATETIME     NULL,
                                    is_deleted       BIT          NOT NULL DEFAULT b'0',
                                    updated_at       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                    additional_info  VARCHAR(255),
                                    match_percentage INT,
                                    match_type       VARCHAR(255),
                                    reference_id     BIGINT,
                                    user_id          BIGINT       NOT NULL,
                                    PRIMARY KEY (id),
                                    FOREIGN KEY (user_id) REFERENCES users(id)
) ENGINE=InnoDB;

CREATE TABLE appointments (
                              id               BIGINT       NOT NULL AUTO_INCREMENT,
                              created_at       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              deleted_at       DATETIME     NULL,
                              is_deleted       BIT          NOT NULL DEFAULT b'0',
                              updated_at       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                              appointment_time DATETIME     NOT NULL,
                              email            VARCHAR(100) NOT NULL,
                              full_name        VARCHAR(100) NOT NULL,
                              phone            VARCHAR(20)  NOT NULL,
                              status           VARCHAR(50)  NOT NULL,
                              expert_id        BIGINT       NOT NULL,
                              user_id          BIGINT,
                              PRIMARY KEY (id),
                              FOREIGN KEY (expert_id) REFERENCES users(id),
                              FOREIGN KEY (user_id)   REFERENCES users(id)
) ENGINE=InnoDB;

CREATE TABLE consultation_notes (
                                    id             BIGINT       NOT NULL AUTO_INCREMENT,
                                    created_at     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                    deleted_at     DATETIME     NULL,
                                    is_deleted     BIT          NOT NULL DEFAULT b'0',
                                    updated_at     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                    note           TEXT,
                                    appointment_id BIGINT       NOT NULL,
                                    expert_id      BIGINT       NOT NULL,
                                    PRIMARY KEY (id),
                                    FOREIGN KEY (appointment_id) REFERENCES appointments(id),
                                    FOREIGN KEY (expert_id)       REFERENCES expert_profiles(id)
) ENGINE=InnoDB;

-- 8. Subscriptions & billing
CREATE TABLE user_subscriptions (
                                    id            BIGINT       NOT NULL AUTO_INCREMENT,
                                    created_at    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                    deleted_at    DATETIME     NULL,
                                    is_deleted    BIT          NOT NULL DEFAULT b'0',
                                    updated_at    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                    end_date      DATE,
                                    is_active     BIT          NOT NULL DEFAULT b'1',
                                    start_date    DATE,
                                    package_id    BIGINT       NOT NULL,
                                    user_id       BIGINT       NOT NULL,
                                    PRIMARY KEY (id),
                                    FOREIGN KEY (package_id) REFERENCES subscription_packages(id),
                                    FOREIGN KEY (user_id)       REFERENCES users(id)
) ENGINE=InnoDB;

CREATE TABLE invoices (
                          id             BIGINT       NOT NULL AUTO_INCREMENT,
                          created_at     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          deleted_at     DATETIME     NULL,
                          is_deleted     BIT          NOT NULL DEFAULT b'0',
                          updated_at     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          code           VARCHAR(255),
                          expired_at     DATETIME,
                          note           VARCHAR(255),
                          package_id     BIGINT       NOT NULL,
                          paid_at        DATETIME,
                          status         VARCHAR(50),
                          total_amount   DOUBLE,
                          user_id        BIGINT       NOT NULL,
                          PRIMARY KEY (id),
                          FOREIGN KEY (package_id) REFERENCES subscription_packages(id),
                          FOREIGN KEY (user_id)       REFERENCES users(id)
) ENGINE=InnoDB;

CREATE TABLE invoice_items (
                               id              BIGINT       NOT NULL AUTO_INCREMENT,
                               created_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               deleted_at      DATETIME     NULL,
                               is_deleted      BIT          NOT NULL DEFAULT b'0',
                               updated_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                               description     VARCHAR(255),
                               item_name       VARCHAR(255),
                               price           DOUBLE,
                               quantity        INT,
                               reference_id    BIGINT,
                               reference_type  VARCHAR(255),
                               invoice_id      BIGINT       NOT NULL,
                               PRIMARY KEY (id),
                               FOREIGN KEY (invoice_id) REFERENCES invoices(id)
) ENGINE=InnoDB;

-- Updated Payments table
CREATE TABLE payments (
                          id               BIGINT       NOT NULL AUTO_INCREMENT,
                          created_at       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          deleted_at       DATETIME     NULL,
                          is_deleted       BIT          NOT NULL DEFAULT b'0',
                          updated_at       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

                          provider         VARCHAR(255) NOT NULL,
                          checkout_url     VARCHAR(512),
                          payment_link_id  VARCHAR(255),
                          transaction_id   VARCHAR(255),
                          response_signature VARCHAR(128),
                          bank_code        VARCHAR(255),
                          card_type        VARCHAR(255),
                          paid_at          DATETIME,
                          status           VARCHAR(50)  NOT NULL,
                          response_code    VARCHAR(255),

                          invoice_id       BIGINT       NOT NULL,
                          user_id          BIGINT       NOT NULL,

                          PRIMARY KEY (id),
                          UNIQUE KEY uq_payments_invoice (invoice_id),
                          INDEX idx_payments_user (user_id),

                          FOREIGN KEY (invoice_id) REFERENCES invoices(id),
                          FOREIGN KEY (user_id)       REFERENCES users(id)
) ENGINE=InnoDB;

-- 9. Logs & feedback & chat
CREATE TABLE feedbacks (
                           id           BIGINT       NOT NULL AUTO_INCREMENT,
                           content      TEXT,
                           created_at   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           user_id      BIGINT       NOT NULL,
                           PRIMARY KEY (id),
                           FOREIGN KEY (user_id) REFERENCES users(id)
) ENGINE=InnoDB;

CREATE TABLE ai_model_logs (
                               id           BIGINT       NOT NULL AUTO_INCREMENT,
                               created_at   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               input        TEXT,
                               model_name   VARCHAR(255),
                               output       TEXT,
                               user_id      BIGINT       NOT NULL,
                               PRIMARY KEY (id),
                               FOREIGN KEY (user_id) REFERENCES users(id)
) ENGINE=InnoDB;

CREATE TABLE system_logs (
                             id           BIGINT       NOT NULL AUTO_INCREMENT,
                             action       VARCHAR(255),
                             created_at   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
                             metadata     TEXT,
                             user_id      BIGINT,
                             PRIMARY KEY (id),
                             FOREIGN KEY (user_id) REFERENCES users(id)
) ENGINE=InnoDB;

CREATE TABLE chats (
                       id            BIGINT       NOT NULL AUTO_INCREMENT,
                       created_at    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       deleted_at    DATETIME     NULL,
                       is_deleted    BIT          NOT NULL DEFAULT b'0',
                       updated_at    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       is_read       BIT          NOT NULL DEFAULT b'0',
                       message       TEXT,
                       message_type  VARCHAR(255),
                       reaction      VARCHAR(255),
                       sender_id     BIGINT       NOT NULL,
                       receiver_id   BIGINT       NOT NULL,
                       PRIMARY KEY (id),
                       FOREIGN KEY (sender_id)   REFERENCES users(id),
                       FOREIGN KEY (receiver_id) REFERENCES users(id)
) ENGINE=InnoDB;
