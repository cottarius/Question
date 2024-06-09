CREATE TABLE questions (
    id BIGSERIAL PRIMARY KEY ,
    question VARCHAR(4096) NOT NULL ,
    is_impotent BOOLEAN NOT NULL
);