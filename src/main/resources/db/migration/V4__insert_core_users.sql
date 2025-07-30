-- V4__insert_core_users.sql

-- users
INSERT IGNORE INTO users (
  id, username, email, password_hash, created_at, updated_at, is_active
) VALUES
  (1, 'alice123', 'alice@gmail.com',
   '$2a$12$F6sIdXunhLINGaayPdNs6OH.1DbhzhicZsnA7hx2gmJE1QzdNXGHW',
   NOW(), NOW(), TRUE),
  (2, 'bob_the_expert', 'bob.expert@goldyai.com',
   '$2a$12$F6sIdXunhLINGaayPdNs6OH.1DbhzhicZsnA7hx2gmJE1QzdNXGHW',
   NOW(), NOW(), TRUE),
  (3, 'glody_admin', 'glody.admin@goldyai.com',
   '$2a$12$F6sIdXunhLINGaayPdNs6OH.1DbhzhicZsnA7hx2gmJE1QzdNXGHW',
   NOW(), NOW(), TRUE),
  (4, 'diana_l', 'diana@gmail.com',
   '$2a$12$F6sIdXunhLINGaayPdNs6OH.1DbhzhicZsnA7hx2gmJE1QzdNXGHW',
   NOW(), NOW(), TRUE),
  (5, 'edward_k', 'edward@gmail.com',
   '$2a$12$F6sIdXunhLINGaayPdNs6OH.1DbhzhicZsnA7hx2gmJE1QzdNXGHW',
   NOW(), NOW(), TRUE);