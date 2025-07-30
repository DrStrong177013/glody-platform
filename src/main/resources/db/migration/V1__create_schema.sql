-- V1__create_schema.sql (updated)

-- === Lookup / Reference Data ===
CREATE TABLE countries (
                           id           BIGINT       NOT NULL PRIMARY KEY,
                           code         VARCHAR(2)   NOT NULL UNIQUE,
                           name         VARCHAR(100) NOT NULL,
                           created_at   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           updated_at   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                           is_active    BOOLEAN      NOT NULL DEFAULT TRUE
);

CREATE TABLE roles (
                       id           BIGINT       PRIMARY KEY,
                       role_name    VARCHAR(50)  NOT NULL UNIQUE,
                       description  VARCHAR(255),
                       created_at   DATETIME     NOT NULL,
                       updated_at   DATETIME     NOT NULL,
                       is_active    BOOLEAN      NOT NULL
);

CREATE TABLE categories (
                            id           BIGINT       PRIMARY KEY,
                            name         VARCHAR(100) NOT NULL,
                            slug         VARCHAR(100) NOT NULL UNIQUE,
                            description  VARCHAR(255),
                            created_at   DATETIME     NOT NULL,
                            updated_at   DATETIME     NOT NULL,
                            is_active    BOOLEAN      NOT NULL
);

CREATE TABLE tags (
                      id           BIGINT       PRIMARY KEY,
                      name         VARCHAR(100) NOT NULL,
--                       slug         VARCHAR(100) NOT NULL UNIQUE,
                      created_at   DATETIME     NOT NULL,
                      updated_at   DATETIME     NOT NULL,
                      is_active    BOOLEAN      NOT NULL
);

CREATE TABLE subscription_packages (
                                       id             BIGINT       PRIMARY KEY,
                                       name           VARCHAR(100) NOT NULL,
                                       description    VARCHAR(255),
                                       duration_days  INT          NOT NULL,
                                       features       TEXT,
                                       price          DECIMAL(8,2) NOT NULL,
                                       created_at     DATETIME     NOT NULL,
                                       updated_at     DATETIME     NOT NULL,
                                       is_active      BOOLEAN      NOT NULL
);
-- cần update
-- === Master Entities ===
CREATE TABLE universities (
                              id               BIGINT       PRIMARY KEY,
                              name             VARCHAR(255) NOT NULL,
                              name_en          VARCHAR(255),
                              country_id       BIGINT       NOT NULL,
                              website          VARCHAR(255),
                              established_year INT,
                              introduction     TEXT,
                              address          VARCHAR(255),
                              contact_email    VARCHAR(100),
                              phone_number     VARCHAR(50),
                              logo_url         VARCHAR(255),
                              created_at       DATETIME     NOT NULL,
                              updated_at       DATETIME     NOT NULL,
                              is_active        BOOLEAN      NOT NULL,
                              FOREIGN KEY (country_id) REFERENCES countries(id)
);

-- === Core Users ===
CREATE TABLE users (
                       id             BIGINT       PRIMARY KEY,
                       username       VARCHAR(50)  NOT NULL UNIQUE,
                       email          VARCHAR(100) NOT NULL UNIQUE,
                       password_hash  VARCHAR(255) NOT NULL,
                       avatar_url     VARCHAR(255),
                       full_name      VARCHAR(255),
                       phone          VARCHAR(255),
                       status         BIT          NOT NULL DEFAULT 1,
                       created_at     DATETIME     NOT NULL,
                       updated_at     DATETIME     NOT NULL,
                       is_active      BOOLEAN      NOT NULL
);

-- === User Details ===
CREATE TABLE user_profiles (
                               id                         BIGINT       PRIMARY KEY,
                               user_id                    BIGINT       NOT NULL,
                               full_name                  VARCHAR(255),
                               date_of_birth              DATE,
                               gender                     VARCHAR(10),
                               phone_number               VARCHAR(50),
                               address                    VARCHAR(255),
                               country_id                 BIGINT,
                               university_id              BIGINT,
                               enrollment_year            INT,
                               major                      VARCHAR(100),
                               avatar_url                 VARCHAR(255),
                               education_level            VARCHAR(255),
                               extracurricular_activities VARCHAR(255),
                               gpa                        DOUBLE,
                               gpa_scale                  DOUBLE,
                               nationality                VARCHAR(255),
                               second_language_certificate VARCHAR(255),
                               target_country             VARCHAR(255),
                               target_semester            VARCHAR(255),
                               university_name            VARCHAR(255),
                               target_year                INT,
                               created_at                 DATETIME     NOT NULL,
                               updated_at                 DATETIME     NOT NULL,
                               is_active                  BOOLEAN      NOT NULL,
                               FOREIGN KEY (user_id)       REFERENCES users(id),
                               FOREIGN KEY (country_id)    REFERENCES countries(id),
                               FOREIGN KEY (university_id) REFERENCES universities(id)
);

CREATE TABLE language_certificates (
                                       id               BIGINT       PRIMARY KEY,
                                       profile_id       BIGINT       NOT NULL,
                                       certificate_name VARCHAR(255),
                                       result_level     VARCHAR(255),
                                       score            DOUBLE,
                                       skill            VARCHAR(255),
                                       created_at       DATETIME     NOT NULL,
                                       updated_at       DATETIME     NOT NULL,
                                       is_active        BOOLEAN      NOT NULL,
                                       FOREIGN KEY (profile_id) REFERENCES user_profiles(id)
);

CREATE TABLE expert_profiles (
                                 id                BIGINT       PRIMARY KEY,
                                 profile_id        BIGINT       NOT NULL,
                                 bio               TEXT,
                                 years_of_experience INT,
                                 avatar_url        VARCHAR(255),
                                 experience        VARCHAR(255),
                                 expertise         VARCHAR(255),
                                 created_at        DATETIME     NOT NULL,
                                 updated_at        DATETIME     NOT NULL,
                                 is_active         BOOLEAN      NOT NULL,
                                 FOREIGN KEY (profile_id) REFERENCES user_profiles(id)
);

CREATE TABLE expert_countries (
                                  id                BIGINT       PRIMARY KEY,
                                  expert_profile_id BIGINT       NOT NULL,
                                  country_id        BIGINT       NOT NULL,
                                  created_at        DATETIME     NOT NULL,
                                  updated_at        DATETIME     NOT NULL,
                                  is_active         BOOLEAN      NOT NULL,
                                  FOREIGN KEY (expert_profile_id) REFERENCES expert_profiles(id),
                                  FOREIGN KEY (country_id)        REFERENCES countries(id)
);

CREATE TABLE user_roles (
                            id          BIGINT       PRIMARY KEY,
                            user_id     BIGINT       NOT NULL,
                            role_id     BIGINT       NOT NULL,
                            created_at  DATETIME     NOT NULL,
                            updated_at  DATETIME     NOT NULL,
                            is_active   BOOLEAN      NOT NULL,
                            FOREIGN KEY (user_id) REFERENCES users(id),
                            FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE user_subscriptions (
                                    id           BIGINT       PRIMARY KEY,
                                    user_id      BIGINT       NOT NULL,
                                    package_id   BIGINT       NOT NULL,
                                    start_date   DATE,
                                    end_date     DATE,
                                    created_at   DATETIME     NOT NULL,
                                    updated_at   DATETIME     NOT NULL,
                                    is_active    BOOLEAN      NOT NULL,
                                    FOREIGN KEY (user_id)    REFERENCES users(id),
                                    FOREIGN KEY (package_id) REFERENCES subscription_packages(id)
);

-- === Content ===
CREATE TABLE posts (
                       id            BIGINT       PRIMARY KEY,
                       user_id       BIGINT       NOT NULL,
                       category_id   BIGINT       NOT NULL,
                       country_id    BIGINT,
                       title         VARCHAR(255) NOT NULL,
                       slug          VARCHAR(255) NOT NULL UNIQUE,
                       excerpt       TEXT,
                       content       TEXT,
                       published_at  DATETIME,
                       publish_date  DATETIME,
                       published     BIT          NOT NULL DEFAULT 0,
                       thumbnail_url VARCHAR(255),
                       view_count    INT          NOT NULL DEFAULT 0,
                       created_at    DATETIME     NOT NULL,
                       updated_at    DATETIME     NOT NULL,
                       is_active     BOOLEAN      NOT NULL,
                       FOREIGN KEY (user_id)     REFERENCES users(id),
                       FOREIGN KEY (category_id) REFERENCES categories(id),
                       FOREIGN KEY (country_id)  REFERENCES countries(id)
);

CREATE TABLE post_tags (
                           id          BIGINT       PRIMARY KEY,
                           post_id     BIGINT       NOT NULL,
                           tag_id      BIGINT       NOT NULL,
                           created_at  DATETIME     NOT NULL,
                           updated_at  DATETIME     NOT NULL,
                           is_active   BOOLEAN      NOT NULL,
                           FOREIGN KEY (post_id) REFERENCES posts(id),
                           FOREIGN KEY (tag_id)  REFERENCES tags(id)
);

CREATE TABLE comments (
                          id          BIGINT       PRIMARY KEY,
                          post_id     BIGINT       NOT NULL,
                          user_id     BIGINT       NOT NULL,
                          parent_id   BIGINT,
                          content     TEXT,
                          created_at  DATETIME     NOT NULL,
                          updated_at  DATETIME     NOT NULL,
                          is_active   BOOLEAN      NOT NULL,
                          FOREIGN KEY (post_id)   REFERENCES posts(id),
                          FOREIGN KEY (user_id)   REFERENCES users(id),
                          FOREIGN KEY (parent_id) REFERENCES comments(id)
);
-- update 2
-- === Scheduling & Matching ===
CREATE TABLE appointments (
                              id                 BIGINT       PRIMARY KEY,
                              user_id            BIGINT       NOT NULL,
                              expert_id  BIGINT       NOT NULL,
                              appointment_time       DATETIME,
                              status             VARCHAR(50),
--                               topic              VARCHAR(255),
                              email              VARCHAR(100),
                              full_name          VARCHAR(100),
                              phone              VARCHAR(20),
                              created_at         DATETIME     NOT NULL,
                              updated_at         DATETIME     NOT NULL,
                              is_active          BOOLEAN      NOT NULL,
                              FOREIGN KEY (user_id)           REFERENCES users(id),
                              FOREIGN KEY (expert_id) REFERENCES users(id)
);

CREATE TABLE consultation_notes (
                                    id                 BIGINT       PRIMARY KEY,
                                    appointment_id     BIGINT       NOT NULL,
                                    expert_id  BIGINT       NOT NULL,
                                    note               TEXT,
                                    created_at         DATETIME     NOT NULL,
                                    updated_at         DATETIME     NOT NULL,
                                    is_active          BOOLEAN      NOT NULL,
                                    FOREIGN KEY (appointment_id)    REFERENCES appointments(id),
                                    FOREIGN KEY (expert_id) REFERENCES users(id)
);
--update 5
CREATE TABLE matching_histories (
                                    id                 BIGINT       PRIMARY KEY,
                                    user_id            BIGINT       NOT NULL,
--                                     expert_profile_id  BIGINT       NOT NULL,
--                                     appointment_id     BIGINT,
--                                     matched_at         DATETIME,
--                                     outcome            VARCHAR(50),
--                                     feedback_rating    INT,
                                    additional_info    VARCHAR(255),
                                    match_percentage   INT,
                                    match_type         VARCHAR(255),
                                    reference_id       BIGINT,
                                    created_at         DATETIME     NOT NULL,
                                    updated_at         DATETIME     NOT NULL,
                                    is_active          BOOLEAN      NOT NULL,
                                    FOREIGN KEY (user_id)           REFERENCES users(id),
                                    FOREIGN KEY (expert_profile_id) REFERENCES expert_profiles(id),
                                    FOREIGN KEY (appointment_id)    REFERENCES appointments(id)
);
--update
-- === Billing & Invoicing ===
CREATE TABLE invoices (
                          id            BIGINT        PRIMARY KEY,
                          user_id       BIGINT        NOT NULL,
                          package_id    BIGINT        NOT NULL,
                          total_amount  DECIMAL(10,2),
--                           currency      VARCHAR(10),
--                           issued_date   DATE,
                          paid_at      DATE,
                          status        VARCHAR(50),
                          code          VARCHAR(255),
                          expired_at    DATETIME,
                          note          VARCHAR(255),
                          created_at    DATETIME      NOT NULL,
                          updated_at    DATETIME      NOT NULL,
                          is_active     BOOLEAN       NOT NULL,
                          FOREIGN KEY (user_id)    REFERENCES users(id),
--                           FOREIGN KEY (package_id) REFERENCES subscription_packages(id)
);
--update
CREATE TABLE invoice_items (
                               id             BIGINT        PRIMARY KEY,
                               invoice_id     BIGINT        NOT NULL,
                               description    VARCHAR(255),
--                                amount         DECIMAL(10,2),
                               quantity       INT,
                               item_name      VARCHAR(255),
                               price          DOUBLE,
                               reference_type VARCHAR(255),
                               reference_id   BIGINT,
                               created_at     DATETIME      NOT NULL,
                               updated_at     DATETIME      NOT NULL,
                               is_active      BOOLEAN       NOT NULL,
                               FOREIGN KEY (invoice_id) REFERENCES invoices(id)
);
--update 3
CREATE TABLE payments (
                          id             BIGINT        PRIMARY KEY,
                          invoice_id     BIGINT        NOT NULL,
                          user_id        BIGINT        NOT NULL,
--                           amount         DECIMAL(10,2),
--                           currency       VARCHAR(10),
--                           method         VARCHAR(50),
                          paid_at        DATETIME,
                          status         VARCHAR(50),
                          bank_code      VARCHAR(255),
                          card_type      VARCHAR(255),
                          provider       VARCHAR(255),
                          response_code  VARCHAR(255),
                          transaction_id VARCHAR(255),
                          created_at     DATETIME      NOT NULL,
                          updated_at     DATETIME      NOT NULL,
                          is_active      BOOLEAN       NOT NULL,
                          FOREIGN KEY (invoice_id) REFERENCES invoices(id),
                          FOREIGN KEY (user_id)     REFERENCES users(id)
);

-- === Logs & Feedback ===
CREATE TABLE feedbacks (
                           id           BIGINT       PRIMARY KEY,
                           sender_id    BIGINT       NOT NULL,
                           receiver_id  BIGINT       NOT NULL,
                           feedback_text TEXT,
                           rating       INT,
                           created_at   DATETIME     NOT NULL,
                           updated_at   DATETIME     NOT NULL,
                           is_active    BOOLEAN      NOT NULL,
                           FOREIGN KEY (sender_id)   REFERENCES users(id),
                           FOREIGN KEY (receiver_id) REFERENCES users(id)
);
-- update
CREATE TABLE ai_model_logs (
                               id           BIGINT       PRIMARY KEY,
                               user_id      BIGINT       NOT NULL,
                               model_name   VARCHAR(100),
                               input_prompt TEXT,
                               output_text  TEXT,
--                                duration_ms  INT,
                               created_at   DATETIME     NOT NULL,
                               FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE system_logs (
                             id           BIGINT       PRIMARY KEY,
                             user_id      BIGINT,
                             action       VARCHAR(100),
                             metadata      TEXT,
--                              ip_address   VARCHAR(45),
                             created_at   DATETIME     NOT NULL,
--                              updated_at   DATETIME     NOT NULL,
--                              is_active    BOOLEAN      NOT NULL,
                             FOREIGN KEY (user_id) REFERENCES users(id)
);
--update
-- === Chat Messages ===
CREATE TABLE chats (
                       id            BIGINT       NOT NULL PRIMARY KEY,
                       created_at    DATETIME     NOT NULL,
                       updated_at    DATETIME     NOT NULL,
                       is_read       BIT          NOT NULL DEFAULT 0,
                       message_type  VARCHAR(255),
                       message,       TEXT,
                       reaction      VARCHAR(255),
                       sender_id     BIGINT       NOT NULL,
                       receiver_id   BIGINT       NOT NULL,
                       FOREIGN KEY (sender_id)   REFERENCES users(id),
                       FOREIGN KEY (receiver_id) REFERENCES users(id)
);


