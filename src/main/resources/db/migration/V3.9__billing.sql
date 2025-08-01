-- V3_09_billing.sql

-- 1) USER_SUBSCRIPTIONS (6 bản ghi)
INSERT IGNORE INTO user_subscriptions
  (id, created_at, deleted_at, is_deleted, updated_at,
   start_date, end_date, is_active, package_id, user_id)
VALUES
  (1, NOW(), NULL, b'0', NOW(),'2025-07-01','2025-07-31',b'1',1,1),
  (2, NOW(), NULL, b'0', NOW(),'2025-06-01','2025-08-29',b'1',1,2),
  (3, NOW(), NULL, b'0', NOW(),'2025-05-01','2025-10-01',b'1',1,3),
  (4, NOW(), NULL, b'0', NOW(),'2025-07-10','2026-07-09',b'1',3,4),
  (5, NOW(), NULL, b'0', NOW(),'2025-08-01','2026-08-01',b'1',2,5),
  (6, NOW(), NULL, b'0', NOW(),'2025-09-01','2025-09-08',b'1',1,6);

-- 2) INVOICES (2 bản ghi)
INSERT IGNORE INTO invoices
  (id, created_at, deleted_at, is_deleted, updated_at,
   code, expired_at, note, package_id, paid_at, status, total_amount, user_id)
VALUES
  -- BASIC subscription of user_id=5
  (1, NOW(), NULL, b'0', NOW(),
   'INV202506002','2025-09-01',NULL,
   2, '2025-06-01','PAID',  59000.00, 5),

  -- PREMIUM subscription of user_id=4
  (2, NOW(), NULL, b'0', NOW(),
   'INV202505003','2025-11-01',NULL,
   3, '2025-05-01','PAID',  99000.00, 4);

-- 3) INVOICE_ITEMS (2 bản ghi)
INSERT IGNORE INTO invoice_items
  (id, created_at, deleted_at, is_deleted, updated_at,
   description, item_name, price, quantity, reference_id, reference_type, invoice_id)
VALUES
  (1, NOW(), NULL, b'0', NOW(),
   'Thanh toán Gói BASIC',   'Gói BASIC',   59000.00, 1, NULL, NULL, 1),
  (2, NOW(), NULL, b'0', NOW(),
   'Thanh toán Gói PREMIUM', 'Gói PREMIUM', 99000.00, 1, NULL, NULL, 2);

-- 4) PAYMENTS (2 bản ghi)
INSERT IGNORE INTO payments
  (id, created_at, deleted_at, is_deleted, updated_at,
   bank_code, card_type, paid_at, provider,
   response_code, status, transaction_id, invoice_id, user_id)
VALUES
  -- PAYMENT for invoice 1 (user 5)
  (1, NOW(), NULL, b'0', NOW(),
   'ACB', 'Mastercard', '2025-06-02', 'PayPal',
   '00', 'SUCCESS', 'TXN20250602', 1, 5),

  -- PAYMENT for invoice 2 (user 4)
  (2, NOW(), NULL, b'0', NOW(),
   'TCB', 'Visa',       '2025-05-01', 'Stripe',
   '00', 'SUCCESS', 'TXN20250501', 2, 4);
