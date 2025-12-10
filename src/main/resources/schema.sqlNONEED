DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS authors;
DROP SEQUENCE IF EXISTS authors_id_seq;

-- Create sequence
CREATE SEQUENCE authors_id_seq
    INCREMENT 1
    MINVALUE 1
    START 1
    CACHE 1;

-- Create authors table
CREATE TABLE authors (
    id BIGINT DEFAULT nextval('authors_id_seq') NOT NULL,
    name TEXT,
    age INTEGER,
    CONSTRAINT authors_pkey PRIMARY KEY (id)
);

-- Create books table
CREATE TABLE books (
    isbn TEXT NOT NULL,
    title TEXT,
    author_id BIGINT,
    CONSTRAINT books_pkey PRIMARY KEY (isbn),
    CONSTRAINT fk_authors FOREIGN KEY (author_id)
        REFERENCES authors (id)
);
