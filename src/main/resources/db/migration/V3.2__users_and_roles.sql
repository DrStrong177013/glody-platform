-- V3_02_users_and_roles.sql

-- 1) CORE USERS (6 bản ghi)
-- mật khẩu mặc định "String_1" đã hash :contentReference[oaicite:1]{index=1}
INSERT IGNORE INTO users
  (id,
   email,
   password_hash,
   created_at,
   deleted_at,
   is_deleted,
   updated_at,
   avatar_url,
   full_name,
   phone,
   status)
VALUES
  (1,
   'glody.admin@goldyai.com',
   '$2a$12$F6sIdXunhLINGaayPdNs6OH.1DbhzhicZsnA7hx2gmJE1QzdNXGHW',
   NOW(), NULL, b'0', NOW(),
   NULL, 'Quản trị hệ thống', '0987654321', b'1'),
  (2,
   'bob.expert@goldyai.com',
   '$2a$12$F6sIdXunhLINGaayPdNs6OH.1DbhzhicZsnA7hx2gmJE1QzdNXGHW',
   NOW(), NULL, b'0', NOW(),
   NULL, 'Bob Chuyên gia', '0912345678', b'1'),
  (3,
   'alice@gmail.com',
   '$2a$12$F6sIdXunhLINGaayPdNs6OH.1DbhzhicZsnA7hx2gmJE1QzdNXGHW',
   NOW(), NULL, b'0', NOW(),
   NULL, 'Nguyễn Thị Alice', '0123456789', b'1'),
  (4,
   'diana@gmail.com',
   '$2a$12$F6sIdXunhLINGaayPdNs6OH.1DbhzhicZsnA7hx2gmJE1QzdNXGHW',
   NOW(), NULL, b'0', NOW(),
   NULL, 'Lê Thị Diana', '098112233',  b'1'),
  (5,
   'edward@gmail.com',
   '$2a$12$F6sIdXunhLINGaayPdNs6OH.1DbhzhicZsnA7hx2gmJE1QzdNXGHW',
   NOW(), NULL, b'0', NOW(),
   NULL, 'Trương Edward', '0911223344', b'1'),
  (6,
   'charlie@gmail.com',
   '$2a$12$F6sIdXunhLINGaayPdNs6OH.1DbhzhicZsnA7hx2gmJE1QzdNXGHW',
   NOW(), NULL, b'0', NOW(),
   NULL, 'Phạm Charlie', '0909988776', b'1');

-- 2) USER_ROLES (9 mapping ≥6)
INSERT IGNORE INTO user_roles (user_id, role_id)
VALUES
  (1,1),    -- admin: ADMIN, REVIEWER
  (2,2),    -- bob_expert: EXPERT, SUPPORT
  (3,3),    -- alice: USER, GUEST
  (4,3),          -- diana: USER
  (5,3),          -- edward: USER
  (6,3);          -- charlie: USER
