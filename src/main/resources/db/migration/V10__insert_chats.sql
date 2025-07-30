-- V10__insert_chats.sql

-- === Chat Messages ===
INSERT IGNORE INTO chats (
  id,
  created_at,
  updated_at,
  is_read,
  message_type,
  content,
  reaction,
  sender_id,
  receiver_id
) VALUES
  (1, '2025-07-10 13:45:00', NOW(), FALSE, 'text',
   'Hello Bob, I have a question about my visa application timeline.',
   NULL, 1, 2),
  (2, '2025-07-10 13:46:30', NOW(), FALSE, 'text',
   'Hi Alice! Sure—what specific deadline are you asking about?',
   NULL, 2, 1),
  (3, '2025-07-10 13:47:15', NOW(), FALSE, 'text',
   'When should I submit my documents to ensure processing before semester start?',
   NULL, 1, 2),
  (4, '2025-07-10 13:48:00', NOW(), FALSE, 'text',
   'Ideally at least three months before. 👍',
   'like', 2, 1),
  (5, '2025-07-10 13:50:00', NOW(), TRUE, 'text',
   'Great, thank you for the tip!',
   NULL, 1, 2);
