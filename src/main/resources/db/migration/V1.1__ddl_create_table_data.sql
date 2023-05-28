DROP TABLE IF EXISTS data;

CREATE TABLE data
(
    id              BIGINT,
    type            VARCHAR(20),
    business_value  VARCHAR,
    created_at      TIMESTAMP,
    updated_at      TIMESTAMP,
    PRIMARY KEY (id)
);
