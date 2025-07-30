-- Thêm cột deleted_at và is_deleted cho tất cả các bảng

-- Lookup / Reference Data
ALTER TABLE countries
    ADD COLUMN deleted_at DATETIME DEFAULT NULL,
ADD COLUMN is_deleted BOOLEAN DEFAULT FALSE;

ALTER TABLE roles
    ADD COLUMN deleted_at DATETIME DEFAULT NULL,
ADD COLUMN is_deleted BOOLEAN DEFAULT FALSE;

ALTER TABLE categories
    ADD COLUMN deleted_at DATETIME DEFAULT NULL,
ADD COLUMN is_deleted BOOLEAN DEFAULT FALSE;

ALTER TABLE tags
    ADD COLUMN deleted_at DATETIME DEFAULT NULL,
ADD COLUMN is_deleted BOOLEAN DEFAULT FALSE;

ALTER TABLE subscription_packages
    ADD COLUMN deleted_at DATETIME DEFAULT NULL,
ADD COLUMN is_deleted BOOLEAN DEFAULT FALSE;

-- Master Entities
ALTER TABLE universities
    ADD COLUMN deleted_at DATETIME DEFAULT NULL,
ADD COLUMN is_deleted BOOLEAN DEFAULT FALSE;

-- Core Users
ALTER TABLE users
    ADD COLUMN deleted_at DATETIME DEFAULT NULL,
ADD COLUMN is_deleted BOOLEAN DEFAULT FALSE;

-- User Details
ALTER TABLE user_profiles
    ADD COLUMN deleted_at DATETIME DEFAULT NULL,
ADD COLUMN is_deleted BOOLEAN DEFAULT FALSE;

ALTER TABLE language_certificates
    ADD COLUMN deleted_at DATETIME DEFAULT NULL,
ADD COLUMN is_deleted BOOLEAN DEFAULT FALSE;

ALTER TABLE expert_profiles
    ADD COLUMN deleted_at DATETIME DEFAULT NULL,
ADD COLUMN is_deleted BOOLEAN DEFAULT FALSE;

ALTER TABLE expert_countries
    ADD COLUMN deleted_at DATETIME DEFAULT NULL,
ADD COLUMN is_deleted BOOLEAN DEFAULT FALSE;

ALTER TABLE user_roles
    ADD COLUMN deleted_at DATETIME DEFAULT NULL,
ADD COLUMN is_deleted BOOLEAN DEFAULT FALSE;

ALTER TABLE user_subscriptions
    ADD COLUMN deleted_at DATETIME DEFAULT NULL,
ADD COLUMN is_deleted BOOLEAN DEFAULT FALSE;

-- Content
ALTER TABLE posts
    ADD COLUMN deleted_at DATETIME DEFAULT NULL,
ADD COLUMN is_deleted BOOLEAN DEFAULT FALSE;

ALTER TABLE post_tags
    ADD COLUMN deleted_at DATETIME DEFAULT NULL,
ADD COLUMN is_deleted BOOLEAN DEFAULT FALSE;

ALTER TABLE comments
    ADD COLUMN deleted_at DATETIME DEFAULT NULL,
ADD COLUMN is_deleted BOOLEAN DEFAULT FALSE;

-- Scheduling & Matching
ALTER TABLE appointments
    ADD COLUMN deleted_at DATETIME DEFAULT NULL,
ADD COLUMN is_deleted BOOLEAN DEFAULT FALSE;

ALTER TABLE consultation_notes
    ADD COLUMN deleted_at DATETIME DEFAULT NULL,
ADD COLUMN is_deleted BOOLEAN DEFAULT FALSE;

ALTER TABLE matching_histories
    ADD COLUMN deleted_at DATETIME DEFAULT NULL,
ADD COLUMN is_deleted BOOLEAN DEFAULT FALSE;

-- Billing & Invoices
ALTER TABLE invoices
    ADD COLUMN deleted_at DATETIME DEFAULT NULL,
ADD COLUMN is_deleted BOOLEAN DEFAULT FALSE;

ALTER TABLE invoice_items
    ADD COLUMN deleted_at DATETIME DEFAULT NULL,
ADD COLUMN is_deleted BOOLEAN DEFAULT FALSE;

ALTER TABLE payments
    ADD COLUMN deleted_at DATETIME DEFAULT NULL,
ADD COLUMN is_deleted BOOLEAN DEFAULT FALSE;

-- Logs & Feedback
ALTER TABLE feedbacks
    ADD COLUMN deleted_at DATETIME DEFAULT NULL,
ADD COLUMN is_deleted BOOLEAN DEFAULT FALSE;

ALTER TABLE ai_model_logs
    ADD COLUMN deleted_at DATETIME DEFAULT NULL,
ADD COLUMN is_deleted BOOLEAN DEFAULT FALSE;

ALTER TABLE system_logs
    ADD COLUMN deleted_at DATETIME DEFAULT NULL,
ADD COLUMN is_deleted BOOLEAN DEFAULT FALSE;

ALTER TABLE chats
    ADD COLUMN is_active BOOLEAN DEFAULT TRUE;

-- Đối với bảng chats, chỉ thêm các cột mới
ALTER TABLE chats
    ADD COLUMN deleted_at DATETIME DEFAULT NULL,
    ADD COLUMN is_deleted BOOLEAN DEFAULT FALSE;

-- Cập nhật dữ liệu: đặt is_deleted = NOT is_active cho tất cả các bảng
UPDATE countries SET is_deleted = NOT is_active;
UPDATE roles SET is_deleted = NOT is_active;
UPDATE categories SET is_deleted = NOT is_active;
UPDATE tags SET is_deleted = NOT is_active;
UPDATE subscription_packages SET is_deleted = NOT is_active;
UPDATE universities SET is_deleted = NOT is_active;
UPDATE users SET is_deleted = NOT is_active;
UPDATE user_profiles SET is_deleted = NOT is_active;
UPDATE language_certificates SET is_deleted = NOT is_active;
UPDATE expert_profiles SET is_deleted = NOT is_active;
UPDATE expert_countries SET is_deleted = NOT is_active;
UPDATE user_roles SET is_deleted = NOT is_active;
UPDATE user_subscriptions SET is_deleted = NOT is_active;
UPDATE posts SET is_deleted = NOT is_active;
UPDATE post_tags SET is_deleted = NOT is_active;
UPDATE comments SET is_deleted = NOT is_active;
UPDATE appointments SET is_deleted = NOT is_active;
UPDATE consultation_notes SET is_deleted = NOT is_active;
UPDATE matching_histories SET is_deleted = NOT is_active;
UPDATE invoices SET is_deleted = NOT is_active;
UPDATE invoice_items SET is_deleted = NOT is_active;
UPDATE payments SET is_deleted = NOT is_active;
UPDATE feedbacks SET is_deleted = NOT is_active;
UPDATE ai_model_logs SET is_deleted = NOT is_active;
UPDATE system_logs SET is_deleted = NOT is_active;
UPDATE chats SET is_deleted = NOT is_active;