-- V3_07_content.sql

-- 1) POSTS (6 bản ghi)
INSERT IGNORE INTO posts
  (id, created_at, deleted_at, is_deleted, updated_at,
   content, excerpt, publish_date, published, slug, thumbnail_url,
   title, view_count, category_id, country_id)
VALUES
  (1,NOW(),NULL,b'0',NOW(),
   'Kinh nghiệm du học Anh tự túc...', 'Chia sẻ kinh nghiệm du học Anh', '2025-07-01',b'1','kinh-nghiem-du-hoc-anh',NULL,
   'Kinh nghiệm du học Anh tự túc', 100,1,2),
  (2,NOW(),NULL,b'0',NOW(),
   'Mẹo xin học bổng Mỹ...', 'Hướng dẫn làm hồ sơ học bổng Mỹ', '2025-06-15',b'1','huong-dan-hoc-bong-my',NULL,
   'Hướng dẫn xin học bổng Mỹ', 80,2,3),
  (3,NOW(),NULL,b'0',NOW(),
   'Tổng hợp visa Úc 2025...', 'Thông tin visa Úc 2025', '2025-05-20',b'1','visa-uc-2025',NULL,
   'Tổng hợp visa Úc 2025', 60,3,4),
  (4,NOW(),NULL,b'0',NOW(),
   'Chuẩn bị phỏng vấn du học', 'Tips phỏng vấn du học', '2025-04-10',b'1','tips-phong-van',NULL,
   'Chuẩn bị phỏng vấn du học', 40,6,1),
  (5,NOW(),NULL,b'0',NOW(),
   'Các loại học bổng', 'Phân loại học bổng du học', '2025-03-05',b'1','cac-loai-hoc-bong',NULL,
   'Các loại học bổng', 120,4,2),
  (6,NOW(),NULL,b'0',NOW(),
   'Kinh nghiệm săn học bổng', 'Cách săn học bổng hiệu quả', '2025-02-28',b'1','san-hoc-bong',NULL,
   'Kinh nghiệm săn học bổng', 90,4,1);

-- 2) POST_TAGS (≥6 bản ghi)
INSERT IGNORE INTO post_tags (post_id, tag_id)
VALUES
  (1,1),(1,4),
  (2,2),(2,5),
  (3,3),(3,6),
  (4,6),(4,1),
  (5,2),(5,4),
  (6,2),(6,5);

-- 3) COMMENTS (6 bản ghi)
INSERT IGNORE INTO comments
  (id, created_at, deleted_at, is_deleted, updated_at,
   content, parent_id, post_id, user_id)
VALUES
  (1,NOW(),NULL,b'0',NOW(),'Bài viết rất hữu ích!',NULL,1,4),
  (2,NOW(),NULL,b'0',NOW(),'Cảm ơn chia sẻ!',1,1,3),
  (3,NOW(),NULL,b'0',NOW(),'Cho hỏi thêm về điều kiện',NULL,2,5),
  (4,NOW(),NULL,b'0',NOW(),'Mình đã thành công xin visa',NULL,3,6),
  (5,NOW(),NULL,b'0',NOW(),'Thư giới thiệu cần format thế nào?',NULL,4,3),
  (6,NOW(),NULL,b'0',NOW(),'Giới thiệu thêm tài liệu tham khảo',NULL,5,6);
