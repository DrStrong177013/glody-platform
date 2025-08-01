-- V3_10_logs.sql

-- 1) FEEDBACKS (6 bản ghi)
INSERT IGNORE INTO feedbacks (id, content, created_at, user_id)
VALUES
  (1,'Rất hữu ích và chi tiết.',NOW(),3),
  (2,'Chuyên gia nhiệt tình.',NOW(),4),
  (3,'Cảm ơn tư vấn chất lượng.',NOW(),5),
  (4,'Mình đã apply thành công.',NOW(),6),
  (5,'Học bổng khá cạnh tranh.',NOW(),3),
  (6,'Visa nhanh chóng.',NOW(),4);

-- 2) AI_MODEL_LOGS (6 bản ghi)
INSERT IGNORE INTO ai_model_logs
  (id, created_at, input, model_name, output, user_id)
VALUES
  (1,NOW(),'Tư vấn chọn ngành phù hợp','gpt-4','Bạn nên chọn Khoa học Máy tính...',3),
  (2,NOW(),'Học bổng Cambridge','gpt-4','Yêu cầu GPA ≥ 3.5...',4),
  (3,NOW(),'Visa Mỹ','gpt-4','Bạn cần chuẩn bị DS-160...',5),
  (4,NOW(),'Hồ sơ học bổng Stanford','gpt-4','Quan trọng thư giới thiệu...',6),
  (5,NOW(),'Điều kiện ANU','gpt-4','Bạn cần IELTS ≥ 6.5...',3),
  (6,NOW(),'Chuẩn bị phỏng vấn','gpt-4','Hãy luyện tập trả lời...',4);

-- 3) SYSTEM_LOGS (6 bản ghi)
INSERT IGNORE INTO system_logs (id, action, created_at, metadata, user_id)
VALUES
  (1,'USER_LOGIN',NOW(),'{"ip":"192.168.1.1"}',3),
  (2,'USER_LOGOUT',NOW(),'{"ip":"192.168.1.1"}',3),
  (3,'PASSWORD_CHANGE',NOW(),'{"method":"profile"}',4),
  (4,'UPDATE_PROFILE',NOW(),'{"field":"major"}',5),
  (5,'SUBSCRIPTION_PURCHASE',NOW(),'{"package":"Gói Premium"}',6),
  (6,'APPOINTMENT_BOOKED',NOW(),'{"appointment_id":1}',3);
