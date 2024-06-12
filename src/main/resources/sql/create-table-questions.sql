CREATE TABLE questions (
    id BIGSERIAL PRIMARY KEY ,
    question VARCHAR(4096) ,
    answer VARCHAR(4096),
    theme varchar(32),
    is_impotent BOOLEAN
);