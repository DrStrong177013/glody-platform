-- V6__insert_content_data.sql

-- posts
INSERT IGNORE INTO posts (
  id, user_id, category_id, country_id, title, slug, excerpt, content,
  published_at, created_at, updated_at, is_active
) VALUES
  (1, 1, 4, 1, '5 Tips to Secure Your Australian Student Visa',
   '5-tips-secure-your-australian-student-visa',
   'Learn the most important steps and deadlines to make your Australian student visa application smooth and successful.',
   'Applying for an Australian student visa can be daunting. In this post, we''ll walk you through the five most crucial steps…',
   '2025-07-05 09:00:00', NOW(), NOW(), TRUE),
  (2, 1, 2, 2, 'Top 10 Universities in Germany for Engineering',
   'top-10-universities-in-germany-for-engineering',
   'A roundup of the best German technical universities offering cutting-edge engineering programs.',
   'Germany is home to world-class engineering schools. Here are the top 10 you should consider…',
   '2025-07-05 10:30:00', NOW(), NOW(), TRUE),
  (3, 2, 3, 3, 'Scholarship Opportunities in Canada You Cannot Miss',
   'scholarship-opportunities-in-canada-you-cant-miss',
   'From government grants to university awards, discover the top scholarships for international students in Canada.',
   'Funding your studies in Canada is easier than you think. In this guide, we explore the best scholarships available…',
   '2025-07-05 11:15:00', NOW(), NOW(), TRUE),
  (4, 5, 2, 4, 'Campus Life at MIT: What to Expect',
   'campus-life-at-mit-what-to-expect',
   'A firsthand look at student life, clubs, and facilities at MIT''s historic campus in Cambridge, USA.',
   'MIT offers a vibrant campus environment. From hackathons to cutting-edge labs…',
   '2025-07-05 13:00:00', NOW(), NOW(), TRUE),
  (5, 3, 1, 4, 'Maximizing Your Study Abroad Application Success',
   'maximizing-your-study-abroad-application-success',
   'Key strategies to improve your application and stand out to admissions committees around the world.',
   'Whether you''re applying to the UK, US, or Canada, a strong application requires…',
   '2025-07-05 14:45:00', NOW(), NOW(), TRUE);

-- post_tags
INSERT IGNORE INTO post_tags (id, post_id, tag_id, created_at, updated_at, is_active) VALUES
  (1, 1, 2, NOW(), NOW(), TRUE),
  (2, 1, 4, NOW(), NOW(), TRUE),
  (3, 2, 5, NOW(), NOW(), TRUE),
  (4, 2, 3, NOW(), NOW(), TRUE),
  (5, 3, 1, NOW(), NOW(), TRUE),
  (6, 3, 3, NOW(), NOW(), TRUE),
  (7, 4, 5, NOW(), NOW(), TRUE),
  (8, 5, 3, NOW(), NOW(), TRUE),
  (9, 5, 1, NOW(), NOW(), TRUE);

-- comments
INSERT IGNORE INTO comments (
  id, post_id, user_id, parent_id, content, created_at, updated_at, is_active
) VALUES
  (1, 1, 2, NULL, 'Thanks for these tips, Alice! Very helpful.', '2025-07-06 10:00:00', NOW(), TRUE),
  (2, 1, 5, NULL, 'Can you elaborate on the timeline for submissions?', '2025-07-06 10:15:00', NOW(), TRUE),
  (3, 1, 1, 2, 'Sure - most applications need to be in at least 3 months before the semester starts.', '2025-07-06 10:20:00', NOW(), TRUE),
  (4, 2, 3, NULL, 'Great list! Would you recommend any for renewable energy?', '2025-07-06 11:00:00', NOW(), TRUE),
  (5, 3, 1, NULL, 'Are these scholarships open to part-time students?', '2025-07-06 11:30:00', NOW(), TRUE);