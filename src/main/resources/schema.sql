-- 1. Enable the AI Brain
-- We enable the 'vector' extension to allow PostgreSQL to do math on arrays of numbers.
-- This effectively turns a standard relational DB into a Vector Database.
CREATE EXTENSION IF NOT EXISTS vector;

CREATE TABLE IF NOT EXISTS documents (
    id BIGSERIAL PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    uploaded_at TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS chunks (
    id BIGSERIAL PRIMARY KEY,
    document_id BIGINT NOT NULL REFERENCES documents(id),
    user_id VARCHAR(255) NOT NULL,
    page_number INT NOT NULL,
    content TEXT NOT NULL,
    embedding vector(768)
);
