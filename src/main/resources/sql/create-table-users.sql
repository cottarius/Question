CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY ,
    username VARCHAR (256) UNIQUE NOT NULL ,
    email VARCHAR (256) UNIQUE NOT NULL,
--     google_id VARCHAR (256) UNIQUE ,
    password VARCHAR (256),
    role VARCHAR (16) NOT NULL
);