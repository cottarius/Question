CREATE TABLE questions (
    id BIGSERIAL PRIMARY KEY ,
    question VARCHAR(4096) NOT NULL ,
    theme varchar(32),
    is_impotent BOOLEAN NOT NULL
);