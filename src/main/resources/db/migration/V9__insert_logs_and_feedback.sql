-- V9__insert_logs_and_feedback.sql

-- feedbacks
INSERT IGNORE INTO feedbacks (
  id, sender_id, receiver_id, feedback_text, rating, created_at, updated_at, is_active
) VALUES
  (1, 1, 2, 'Great session, very helpful advice on visas!', 5, '2025-07-10 16:00:00', NOW(), TRUE),
  (2, 4, 2, 'Bob''s scholarship recommendations were on point.', 4, '2025-07-11 12:00:00', NOW(), TRUE),
  (3, 5, 1, 'Alice''s blog on campus life gave great insights!', 5, '2025-07-05 19:00:00', NOW(), TRUE);

-- ai_model_logs
INSERT IGNORE INTO ai_model_logs (
  id, user_id, model_name, input_prompt, output_text, duration_ms, created_at, updated_at, is_active
) VALUES
  (1, 1, 'gpt-3.5-turbo', 'Summarize scholarship opportunities in Canada',
   'Scholarship opportunities in Canada include government grants, university awards, and private foundation funding for international students.',
   120, '2025-07-05 11:16:00', NOW(), TRUE),
  (2, 3, 'gpt-4o-mini', 'Translate university introduction to Vietnamese',
   'Đại học XYZ là một tổ chức nghiên cứu hàng đầu với nhiều chương trình giảng dạy đa ngành.',
   95, '2025-07-06 14:30:00', NOW(), TRUE);

-- system_logs
INSERT IGNORE INTO system_logs (
  id, user_id, action, details, ip_address, created_at, updated_at, is_active
) VALUES
  (1, 2, 'login', 'User logged in successfully', '203.0.113.10', '2025-07-03 08:00:00', NOW(), TRUE),
  (2, 3, 'password_change', 'User changed password', '203.0.113.11', '2025-07-04 09:15:00', NOW(), TRUE);