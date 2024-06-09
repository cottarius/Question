CREATE TABLE answers (
    id BIGSERIAL PRIMARY KEY,
    answer VARCHAR(4096),
    question_id BIGINT REFERENCES questions(id)
);