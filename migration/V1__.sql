ALTER TABLE programs
DROP
COLUMN language;

ALTER TABLE programs
DROP
COLUMN level;

ALTER TABLE programs
    ADD language VARCHAR(255) NULL;

ALTER TABLE programs
    ADD level VARCHAR(255) NULL;

ALTER TABLE appointments
DROP
COLUMN status;

ALTER TABLE appointments
    ADD status VARCHAR(255) NULL;

ALTER TABLE invoices
DROP
COLUMN status;

ALTER TABLE invoices
    ADD status VARCHAR(255) NULL;

ALTER TABLE payments
DROP
COLUMN status;

ALTER TABLE payments
    ADD status VARCHAR(255) NULL;