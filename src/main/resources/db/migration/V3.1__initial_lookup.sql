-- V3_01_initial_lookup.sql

-- 1. COUNTRIES (6 bản ghi)
INSERT IGNORE INTO countries
  (id, created_at, deleted_at, is_deleted, updated_at, code, name)
VALUES
  (1, NOW(), NULL, b'0', NOW(), 'VN', 'Việt Nam'),
  (2, NOW(), NULL, b'0', NOW(), 'UK', 'Vương quốc Anh'),
  (3, NOW(), NULL, b'0', NOW(), 'US', 'Hoa Kỳ'),
  (4, NOW(), NULL, b'0', NOW(), 'AU', 'Úc'),
  (5, NOW(), NULL, b'0', NOW(), 'CA', 'Canada'),
  (6, NOW(), NULL, b'0', NOW(), 'NZ', 'New Zealand');

-- 2. CATEGORIES (6 bản ghi)
INSERT IGNORE INTO categories
  (id, created_at, deleted_at, is_deleted, updated_at, name, slug)
VALUES
  (1, NOW(), NULL, b'0', NOW(), 'Du học Anh',   'du-hoc-anh'),
  (2, NOW(), NULL, b'0', NOW(), 'Du học Mỹ',     'du-hoc-my'),
  (3, NOW(), NULL, b'0', NOW(), 'Du học Úc',     'du-hoc-uc'),
  (4, NOW(), NULL, b'0', NOW(), 'Học bổng',       'hoc-bong'),
  (5, NOW(), NULL, b'0', NOW(), 'Visa',           'visa'),
  (6, NOW(), NULL, b'0', NOW(), 'Kinh nghiệm',    'kinh-nghiem');

-- 3. TAGS (6 bản ghi)
INSERT IGNORE INTO tags
  (id, created_at, deleted_at, is_deleted, updated_at, name)
VALUES
  (1, NOW(), NULL, b'0', NOW(), 'IELTS'),
  (2, NOW(), NULL, b'0', NOW(), 'Học bổng'),
  (3, NOW(), NULL, b'0', NOW(), 'Visa'),
  (4, NOW(), NULL, b'0', NOW(), 'Kinh nghiệm'),
  (5, NOW(), NULL, b'0', NOW(), 'TOEFL'),
  (6, NOW(), NULL, b'0', NOW(), 'Phỏng vấn');

-- 4. ROLES (6 bản ghi)
INSERT IGNORE INTO roles
  (id, created_at, deleted_at, is_deleted, updated_at, role_name)
VALUES
  (1, NOW(), NULL, b'0', NOW(), 'ADMIN'),
  (2, NOW(), NULL, b'0', NOW(), 'EXPERT'),
  (3, NOW(), NULL, b'0', NOW(), 'STUDENT');

-- 5. SUBSCRIPTION_PACKAGES (6 bản ghi)
INSERT IGNORE INTO subscription_packages
  (id, created_at, deleted_at, is_deleted, updated_at, name, description, duration_days, features, price)
VALUES
  (1, NOW(), NULL, b'0', NOW(),
   'FREE',    'Các tính năng cơ bản',         365, 'Tư vấn cơ bản, 1 lần/tuần',                         0.00),
  (2, NOW(), NULL, b'0', NOW(),
   'BASIC',   'Gói cơ bản trả phí',           31,  'Tư vấn mở rộng, tài liệu học tập',                   59000.00),
  (3, NOW(), NULL, b'0', NOW(),
   'PREMIUM', 'Gói cao cấp đầy đủ tính năng', 93,  'Tư vấn 1-1, ưu tiên hỗ trợ, bộ tài liệu chuyên sâu', 99000.00);
