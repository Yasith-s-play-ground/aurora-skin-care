CREATE TABLE patient
(
    nic   VARCHAR(12) PRIMARY KEY, -- Unique NIC for each patient
    name  VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(15)  NOT NULL UNIQUE
);

CREATE TABLE dermatologist
(
    id    SERIAL PRIMARY KEY, -- Auto-incrementing ID for each dermatologist
    name  VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(15)  NOT NULL UNIQUE
);

CREATE TABLE consultation_time
(
    id          SERIAL PRIMARY KEY,
    day_of_week VARCHAR(10) NOT NULL UNIQUE, -- 'Monday', 'Tuesday', etc.
    start_time  TIME        NOT NULL,
    end_time    TIME        NOT NULL
);

CREATE TABLE treatment
(
    id    SERIAL PRIMARY KEY,
    name  VARCHAR(50)    NOT NULL UNIQUE,
    price DECIMAL(10, 2) NOT NULL
);

CREATE TABLE appointment
(
    id               SERIAL PRIMARY KEY,
    appointment_date DATE        NOT NULL,
    appointment_time TIME        NOT NULL,
    patient_id       VARCHAR(12) NOT NULL,
    dermatologist_id INT         NOT NULL,
    treatment_id     INT         NOT NULL,
    FOREIGN KEY (patient_id) REFERENCES patient (nic),            -- Assuming there's a patient table
    FOREIGN KEY (dermatologist_id) REFERENCES dermatologist (id), -- Assuming there's a dermatologist table
    FOREIGN KEY (treatment_id) REFERENCES treatment (id)          -- From the treatment table
);

CREATE TYPE payment_category AS ENUM ('REGISTRATION', 'FINAL');

CREATE TABLE payment
(
    id             SERIAL PRIMARY KEY,
    appointment_id INT REFERENCES appointment (id),
    category       payment_category NOT NULL,
    amount         DECIMAL(10, 2)   NOT NULL,
    payment_date   TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE invoice
(
    id             SERIAL PRIMARY KEY,                           -- Auto-incrementing ID for the invoice
    payment_id     INT            NOT NULL,                      -- Reference to the payment
    issue_date     DATE           NOT NULL DEFAULT CURRENT_DATE, -- Date the invoice is issued
    total_amount   DECIMAL(10, 2) NOT NULL,                      -- Total amount on the invoice (incl. tax)
    tax_amount     DECIMAL(10, 2) NOT NULL,                      -- Amount of tax included

    CONSTRAINT fk_payment FOREIGN KEY (payment_id) REFERENCES payment (id)
);

INSERT INTO consultation_time (day_of_week, start_time, end_time)
VALUES ('MONDAY', '10:00:00', '13:00:00'),
       ('WEDNESDAY', '14:00:00', '17:00:00'),
       ('FRIDAY', '16:00:00', '20:00:00'),
       ('SATURDAY', '09:00:00', '13:00:00');

INSERT INTO treatment (name, price)
VALUES ('Acne Treatment', 2750.00),
       ('Skin Whitening', 7650.00),
       ('Mole Removal', 3850.00),
       ('Laser Treatment', 12500.00);

INSERT INTO dermatologist (name, email, phone)
VALUES ('Dr. Sarah Johnson', 'sarah.johnson@yahoo.com', '0712345678'),
       ('Dr. Mark Smith', 'mark.smith@gmail.com', '0723456789');

