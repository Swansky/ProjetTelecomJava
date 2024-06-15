CREATE TABLE IF NOT EXISTS reimbursement
(
    social_security_id    VARCHAR(13)    NOT NULL,
    lastname              VARCHAR(255)   NOT NULL,
    first_name            VARCHAR(255)   NOT NULL,
    birthday              DATE           NOT NULL,
    email                 VARCHAR(255)   NOT NULL,
    phone                 VARCHAR(255)   NOT NULL,
    reimbursement_id      VARCHAR(13)    NOT NULL PRIMARY KEY,
    health_code           VARCHAR(255)   NOT NULL,
    amount                DECIMAL(10, 2) NOT NULL,
    date_of_reimbursement DATE           NOT NULL
);

-- Path: src/test/resources/data.sql


