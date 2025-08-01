-- V3_06_program_extras.sql

-- 1) PROGRAM_MAJORS (6 bản ghi)
INSERT IGNORE INTO program_majors (program_id, major)
VALUES
  (1,'Khoa học Máy tính'),
  (2,'Quản trị Kinh doanh'),
  (3,'Kỹ thuật Điện'),
  (4,'Y học'),
  (5,'Luật'),
  (6,'Toán học');

-- 2) PROGRAM_DOCUMENTS (6 bản ghi)
INSERT IGNORE INTO program_documents (requirement_id, document_name)
VALUES
  (1,'Bảng điểm Đại học'),
  (2,'Giấy khen'),
  (3,'Bằng tốt nghiệp'),
  (4,'Thư giới thiệu'),
  (5,'CV tiếng Anh'),
  (6,'Statement of Purpose');
