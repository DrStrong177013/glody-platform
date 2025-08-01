-- V3_04_master_reference.sql

-- 1) UNIVERSITIES (6 bản ghi)
INSERT IGNORE INTO universities
  (id, created_at, deleted_at, is_deleted, updated_at, name, country_id)
VALUES
  (1, NOW(), NULL, b'0', NOW(), 'Đại học Quốc gia Hà Nội', 1),
  (2, NOW(), NULL, b'0', NOW(), 'University of Cambridge',      2),
  (3, NOW(), NULL, b'0', NOW(), 'Massachusetts Institute of Technology (MIT)', 3),
  (4, NOW(), NULL, b'0', NOW(), 'Australian National University', 4),
  (5, NOW(), NULL, b'0', NOW(), 'Stanford University',            3),
  (6, NOW(), NULL, b'0', NOW(), 'University of Melbourne',       4);

-- 2) SCHOOLS (6 bản ghi) — đã thêm cột country_id
INSERT IGNORE INTO schools
  (id, created_at, deleted_at, is_deleted, updated_at,
   name, name_en, established_year, introduction, location,
   rank_text, website, logo_url, country_id)
VALUES
  (1, NOW(), NULL, b'0', NOW(),
   'Đại học Cambridge', 'University of Cambridge', 1209,
   'Một trong các trường lâu đời nhất thế giới', 'Cambridge, Anh',
   '#7 QS', 'https://www.cam.ac.uk',               'logo1.png', 2),

  (2, NOW(), NULL, b'0', NOW(),
   'Đại học Oxford',   'University of Oxford',   1096,
   'Xếp hạng cao, nhiều học bổng',              'Oxford, Anh',
   '#5 QS', 'https://www.ox.ac.uk',              'logo2.png', 2),

  (3, NOW(), NULL, b'0', NOW(),
   'Stanford University','Stanford University', 1885,
   'Nổi tiếng về khởi nghiệp và công nghệ',      'Stanford, USA',
   '#3 QS', 'https://www.stanford.edu',          'logo3.png', 3),

  (4, NOW(), NULL, b'0', NOW(),
   'Harvard University','Harvard University',   1636,
   'Trường hàng đầu thế giới',                   'Cambridge, USA',
   '#2 QS', 'https://www.harvard.edu',           'logo4.png', 3),

  (5, NOW(), NULL, b'0', NOW(),
   'University of Toronto','University of Toronto', 1827,
   'Top Canada, đa dạng ngành',                 'Toronto, Canada',
   '#26 QS','https://www.utoronto.ca',           'logo5.png', 5),

  (6, NOW(), NULL, b'0', NOW(),
   'University of Melbourne','University of Melbourne',1853,
   'Top Úc, nghiên cứu mạnh',                    'Melbourne, Úc',
   '#33 QS','https://www.unimelb.edu.au',        'logo6.png', 4);
