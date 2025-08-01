-- V3_08_interactions.sql

-- 1) MATCHING_HISTORIES (6 bản ghi)
INSERT IGNORE INTO matching_histories
  (id, created_at, deleted_at, is_deleted, updated_at,
   additional_info, match_percentage, match_type, reference_id, user_id)
VALUES
  (1,NOW(),NULL,b'0',NOW(),'Đề xuất Khoa học Máy tính tại Cambridge',85,'Ngành học',1,3),
  (2,NOW(),NULL,b'0',NOW(),'Đề xuất Quản trị tại Oxford',78,'Ngành học',2,4),
  (3,NOW(),NULL,b'0',NOW(),'Đề xuất Kỹ thuật MIT',90,'Ngành học',3,5),
  (4,NOW(),NULL,b'0',NOW(),'Đề xuất Y học ANU',82,'Ngành học',4,6),
  (5,NOW(),NULL,b'0',NOW(),'Đề xuất Luật Stanford',88,'Ngành học',5,1),
  (6,NOW(),NULL,b'0',NOW(),'Đề xuất Toán Toronto',80,'Ngành học',6,2);

-- 2) APPOINTMENTS (6 bản ghi)
INSERT IGNORE INTO appointments
  (id, created_at, deleted_at, is_deleted, updated_at,
   appointment_time, email, full_name, phone, status, expert_id, user_id)
VALUES
  (1,NOW(),NULL,b'0',NOW(),'2025-07-05 14:00','alice@gmail.com','Nguyễn Thị Alice','0123456789','CONFIRMED',2,3),
  (2,NOW(),NULL,b'0',NOW(),'2025-07-06 10:00','diana@gmail.com','Lê Thị Diana','098112233','COMPLETED',2,4),
  (3,NOW(),NULL,b'0',NOW(),'2025-07-07 09:00','edward@gmail.com','Trương Edward','0911223344','PENDING',2,5),
  (4,NOW(),NULL,b'0',NOW(),'2025-07-08 16:00','charlie@gmail.com','Phạm Charlie','0909988776','CONFIRMED',2,6),
  (5,NOW(),NULL,b'0',NOW(),'2025-07-09 11:00','alice@gmail.com','Nguyễn Thị Alice','0123456789','COMPLETED',2,3),
  (6,NOW(),NULL,b'0',NOW(),'2025-07-10 15:00','bob.expert@goldyai.com','Bob Chuyên gia','0912345678','COMPLETED',2,1);

-- 3) CONSULTATION_NOTES (6 bản ghi)
INSERT IGNORE INTO consultation_notes
  (id, created_at, deleted_at, is_deleted, updated_at,
   note, appointment_id, expert_id)
VALUES
  (1,NOW(),NULL,b'0',NOW(),'Buổi 1: Tư vấn chọn trường',1,2),
  (2,NOW(),NULL,b'0',NOW(),'Buổi 2: Chuẩn bị hồ sơ',2,2),
  (3,NOW(),NULL,b'0',NOW(),'Buổi 3: Học bổng',3,2),
  (4,NOW(),NULL,b'0',NOW(),'Buổi 4: Visa',4,2),
  (5,NOW(),NULL,b'0',NOW(),'Buổi 5: Thư giới thiệu',5,2),
  (6,NOW(),NULL,b'0',NOW(),'Buổi 6: Phỏng vấn',6,2);

-- 4) CHATS (6 bản ghi)
INSERT IGNORE INTO chats
  (id, created_at, deleted_at, is_deleted, updated_at,
   is_read, message, message_type, reaction, sender_id, receiver_id)
VALUES
  (1,NOW(),NULL,b'0',NOW(),b'0','Xin chào, tôi muốn hỏi về chương trình...','text',NULL,3,2),
  (2,NOW(),NULL,b'0',NOW(),b'1','Chào bạn, tôi là Bob...','text',NULL,2,3),
  (3,NOW(),NULL,b'0',NOW(),b'0','Bạn cần giúp gì?','text',NULL,2,4),
  (4,NOW(),NULL,b'0',NOW(),b'0','Mình cần tư vấn học bổng','text',NULL,4,2),
  (5,NOW(),NULL,b'0',NOW(),b'1','OK, mình sẽ gửi tài liệu','text',NULL,2,4),
  (6,NOW(),NULL,b'0',NOW(),b'0','Cảm ơn bạn!','text',NULL,4,2);
