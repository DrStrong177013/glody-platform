-- V7__insert_scheduling_and_matching.sql

-- === Appointments ===
INSERT IGNORE INTO appointments (
  id,
  user_id,
  expert_profile_id,
  scheduled_at,
  status,
  topic,
  email,
  full_name,
  phone,
  created_at,
  updated_at,
  is_active
) VALUES
  (1, 1, 1, '2025-07-10 14:00:00', 'confirmed', 'Visa application process for Australia',
   'alice@gmail.com', 'Alice Nguyễn', '+84 912 345 678',
   NOW(), NOW(), TRUE),
  (2, 4, 1, '2025-07-11 10:30:00', 'pending',   'Scholarship options in Canada',
   'diana@gmail.com', 'Diana Lê',     '+84 915 666 777',
   NOW(), NOW(), TRUE),
  (3, 5, 1, '2025-07-12 16:15:00', 'canceled', 'University ranking comparison',
   'edward@gmail.com','Edward King',  '+84 916 888 999',
   NOW(), NOW(), FALSE);

-- === Consultation Notes ===
INSERT IGNORE INTO consultation_notes (
  id,
  appointment_id,
  expert_profile_id,
  note,
  created_at,
  updated_at,
  is_active
) VALUES
  (1, 1, 1,
   'Explained student visa subclass 500 requirements and documentation needed.',
   '2025-07-10 15:30:00', NOW(), TRUE),
  (2, 2, 1,
   'Provided list of top scholarship programs and their deadlines.',
   '2025-07-11 11:45:00', NOW(), TRUE);

-- === Matching Histories ===
INSERT IGNORE INTO matching_histories (
  id,
  user_id,
  expert_profile_id,
  appointment_id,
  matched_at,
  outcome,
  feedback_rating,
  additional_info,
  match_percentage,
  match_type,
  reference_id,
  created_at,
  updated_at,
  is_active
) VALUES
  (1, 1, 1, 1, '2025-07-06 07:55:00', 'success', 5,
   'Matched via priority algorithm', 95, 'automatic', 1,
   '2025-07-06 07:55:00', NOW(), TRUE),
  (2, 4, 1, 2, '2025-07-06 08:03:00', 'pending', NULL,
   'Awaiting expert confirmation', 80, 'automatic', 2,
   '2025-07-06 08:03:00', NOW(), TRUE);
