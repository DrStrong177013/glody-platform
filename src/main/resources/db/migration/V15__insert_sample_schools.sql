-- V15__insert_sample_schools.sql

INSERT INTO schools
(name, name_en, established_year, location, logo_url, introduction, rank_text, website)
VALUES
    ('Đại học Quốc gia Hà Nội',
     'Vietnam National University, Hanoi',
     1906,
     'Hà Nội, Việt Nam',
     'https://example.com/logos/vnu.png',
     'Đại học Quốc gia Hà Nội là trung tâm đào tạo và nghiên cứu hàng đầu tại Việt Nam.',
     '#1 Vietnam University',
     'http://vnu.edu.vn'),
    ('University of Oxford',
     'University of Oxford',
     1096,
     'Oxford, United Kingdom',
     'https://example.com/logos/oxford.png',
     'The University of Oxford is a collegiate research university in Oxford, England.',
     '1st in the world',
     'http://www.ox.ac.uk'),
    ('Massachusetts Institute of Technology',
     'Massachusetts Institute of Technology',
     1861,
     'Cambridge, MA, USA',
     'https://example.com/logos/mit.png',
     'MIT is a private land-grant research university in Cambridge, Massachusetts.',
     '5th in the world',
     'http://web.mit.edu');
