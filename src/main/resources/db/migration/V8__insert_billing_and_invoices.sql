-- V8__insert_billing_and_invoices.sql

-- === Invoices ===
INSERT IGNORE INTO invoices (
  id,
  user_id,
  package_id,
  total_amount,
  currency,
  issued_date,
  due_date,
  status,
  code,
  expired_at,
  note,
  created_at,
  updated_at,
  is_active
) VALUES
  (1, 1, 1,   0.00,  'USD', '2025-07-03', '2025-07-10', 'paid',
   'INV-0001', '2025-07-10 23:59:59', 'Free trial plan invoice', NOW(), NOW(), TRUE),
  (2, 2, 2,  19.99,  'USD', '2025-07-01', '2025-07-08', 'paid',
   'INV-0002', '2025-07-08 23:59:59', 'Basic monthly subscription', NOW(), NOW(), TRUE),
  (3, 3, 4, 199.99,  'USD', '2025-06-01', '2025-06-08', 'paid',
   'INV-0003', '2025-06-08 23:59:59', 'Premium annual plan', NOW(), NOW(), TRUE),
  (4, 4, 3,  99.99,  'USD', '2025-05-01', '2025-05-08', 'overdue',
   'INV-0004', '2025-05-08 23:59:59', 'Pro 6-month subscription', NOW(), NOW(), TRUE),
  (5, 5, 2,  19.99,  'USD', '2025-07-01', '2025-07-08', 'pending',
   'INV-0005', '2025-07-08 23:59:59', 'Basic monthly subscription', NOW(), NOW(), TRUE);

-- === Invoice Items ===
INSERT IGNORE INTO invoice_items (
  id,
  invoice_id,
  description,
  amount,
  quantity,
  item_name,
  price,
  reference_type,
  reference_id,
  created_at,
  updated_at,
  is_active
) VALUES
  (1, 1, 'Free Trial Subscription',       0.00, 1,
   'Free Trial Subscription',       0.00,
   'subscription_packages', 1,
   NOW(), NOW(), TRUE),
  (2, 2, 'Basic Monthly Subscription',   19.99, 1,
   'Basic Monthly Subscription',   19.99,
   'subscription_packages', 2,
   NOW(), NOW(), TRUE),
  (3, 3, 'Premium Annual Subscription', 199.99, 1,
   'Premium Annual Subscription', 199.99,
   'subscription_packages', 4,
   NOW(), NOW(), TRUE),
  (4, 4, 'Pro 6-Month Subscription',     99.99, 1,
   'Pro 6-Month Subscription',     99.99,
   'subscription_packages', 3,
   NOW(), NOW(), TRUE),
  (5, 5, 'Basic Monthly Subscription',   19.99, 1,
   'Basic Monthly Subscription',   19.99,
   'subscription_packages', 2,
   NOW(), NOW(), TRUE);

-- === Payments ===
INSERT IGNORE INTO payments (
  id,
  invoice_id,
  user_id,
  amount,
  currency,
  method,
  paid_at,
  status,
  bank_code,
  card_type,
  provider,
  response_code,
  transaction_id,
  created_at,
  updated_at,
  is_active
) VALUES
  (1, 1, 1,   0.00,  'USD', 'credit_card',
   '2025-07-03 12:30:00', 'completed',
   'BK01', 'Visa', 'Stripe', '00', 'TXN1001', NOW(), NOW(), TRUE),
  (2, 2, 2,  19.99,  'USD', 'paypal',
   '2025-07-03 12:35:00', 'completed',
   NULL, 'N/A', 'PayPal', '00', 'TXN1002', NOW(), NOW(), TRUE),
  (3, 3, 3, 199.99,  'USD', 'bank_transfer',
   '2025-06-02 09:00:00', 'completed',
   'BT01', 'N/A', 'BankTransfer', '00', 'TXN1003', NOW(), NOW(), TRUE),
  (4, 4, 4,  50.00,  'USD', 'credit_card',
   '2025-06-10 14:00:00', 'partial',
   'BK02', 'MasterCard', 'Stripe', 'P01', 'TXN1004', NOW(), NOW(), TRUE);
