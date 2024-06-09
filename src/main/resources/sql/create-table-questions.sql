CREATE TABLE questions (
    id BIGSERIAL PRIMARY KEY ,
    question VARCHAR(4096) NOT NULL ,
    answer VARCHAR(4096),
    is_impotent BOOLEAN NOT NULL
);