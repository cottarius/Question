CREATE TABLE answers (
    id BIGSERIAL PRIMARY KEY,
    answer VARCHAR(4096),
    theme VARCHAR(32),
    question_id BIGINT REFERENCES questions(id)
);