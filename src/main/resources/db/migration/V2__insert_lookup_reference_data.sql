-- V2__insert_lookup_reference_data.sql

-- countries
INSERT INTO countries (id, code, name, created_at, updated_at, is_active) VALUES
(1, 'AU', 'Australia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, TRUE),
(2, 'DE', 'Germany', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, TRUE),
(3, 'CA', 'Canada', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, TRUE),
(4, 'US', 'United States', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, TRUE),
(5, 'UK', 'United Kingdom', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, TRUE);

-- roles
INSERT INTO roles (id, role_name, description, created_at, updated_at, is_active) VALUES
(1, 'student', 'Người dùng đăng ký học bổng và tư vấn du học', NOW(), NOW(), TRUE),
(2, 'expert', 'Chuyên gia tư vấn du học', NOW(), NOW(), TRUE),
(3, 'admin', 'Quản trị viên hệ thống', NOW(), NOW(), TRUE);

-- categories
INSERT INTO categories (id, name, slug, description, created_at, updated_at, is_active) VALUES
(1, 'Study Abroad Tips', 'study-abroad-tips', 'Mẹo và kinh nghiệm du học', NOW(), NOW(), TRUE),
(2, 'University Reviews', 'university-reviews', 'Đánh giá các trường đại học', NOW(), NOW(), TRUE),
(3, 'Scholarships', 'scholarships', 'Thông tin học bổng', NOW(), NOW(), TRUE),
(4, 'Visa Regulations', 'visa-regulations', 'Quy định visa và thủ tục', NOW(), NOW(), TRUE);

-- tags
INSERT INTO tags (id, name, slug, created_at, updated_at, is_active) VALUES
(1, 'scholarship', 'scholarship', NOW(), NOW(), TRUE),
(2, 'visa', 'visa', NOW(), NOW(), TRUE),
(3, 'application', 'application', NOW(), NOW(), TRUE),
(4, 'deadline', 'deadline', NOW(), NOW(), TRUE),
(5, 'campus-life', 'campus-life', NOW(), NOW(), TRUE);

-- subscription_packages
INSERT INTO subscription_packages (id, name, description, duration_days, features, price, created_at, updated_at, is_active) VALUES
(1, 'Free Trial', 'Access to basic features for 7 days', 7, 'Basic Search, Email Alerts', 0.00, NOW(), NOW(), TRUE),
(2, 'Basic', 'Monthly subscription with core features', 30, 'Full Search, Save Favorites', 19.99, NOW(), NOW(), TRUE),
(3, 'Pro', '6-month plan with priority support', 180, 'All Basic, Priority Support, Exclusive Content', 99.99, NOW(), NOW(), TRUE),
(4, 'Premium', 'Annual plan with 1:1 consultation', 365, 'All Pro, 1:1 Expert Consultation', 199.99, NOW(), NOW(), TRUE);