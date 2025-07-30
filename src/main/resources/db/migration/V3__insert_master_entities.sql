-- V3__insert_master_entities.sql

-- === Universities ===
INSERT IGNORE INTO universities (
    id,
    name,
    name_en,
    country_id,
    website,
    established_year,
    introduction,
    address,
    contact_email,
    phone_number,
    logo_url,
    created_at,
    updated_at,
    is_active
) VALUES
      (1, 'University of Melbourne',                'University of Melbourne',                1, 'https://www.unimelb.edu.au',          1853,
       'Leading Australian university with a strong research focus across a broad range of disciplines.',
       'Parkville, Melbourne VIC 3010, Australia', 'info@unimelb.edu.au', '+61 3 9035 5511', 'https://www.unimelb.edu.au/logo.png',
       NOW(), NOW(), TRUE),
      (2, 'Technical University of Munich',         'Technical University of Munich',         2, 'https://www.tum.de',                   1868,
       'One of Europe''s top universities for engineering and technology with a strong emphasis on research and innovation.',
       'Arcisstraße 21, 80333 München, Germany',    'info@tum.de',         '+49 89 28901-0',  'https://www.tum.de/logo.png',
       NOW(), NOW(), TRUE),
      (3, 'University of Toronto',                  'University of Toronto',                  3, 'https://www.utoronto.ca',              1827,
       'Canada''s leading comprehensive university, known for its excellence in teaching and research.',
       '27 King’s College Cir, Toronto, ON M5S 1A1, Canada', 'info@utoronto.ca', '+1 416-978-2011', 'https://www.utoronto.ca/logo.png',
       NOW(), NOW(), TRUE),
      (4, 'Massachusetts Institute of Technology', 'MIT',                                    4, 'https://www.mit.edu',                  1861,
       'World-renowned institution for science and technology, driving cutting-edge research and education.',
       '77 Massachusetts Ave, Cambridge, MA 02139, USA', 'admissions@mit.edu', '+1 617-253-1000', 'https://www.mit.edu/logo.png',
       NOW(), NOW(), TRUE),
      (5, 'University of Oxford',                  'University of Oxford',                  5, 'https://www.ox.ac.uk',                  1096,
       'Oldest English-speaking university with a global reputation for excellence in research and teaching.',
       'University Offices, Wellington Square, Oxford OX1 2JD, United Kingdom', 'info@ox.ac.uk', '+44 1865 270000', 'https://www.ox.ac.uk/logo.png',
       NOW(), NOW(), TRUE);
