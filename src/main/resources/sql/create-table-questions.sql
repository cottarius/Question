CREATE TABLE questions (
    id BIGSERIAL PRIMARY KEY ,
    question VARCHAR(4096) ,
    answer VARCHAR(8192),
    theme varchar(32),
    is_impotent BOOLEAN
);