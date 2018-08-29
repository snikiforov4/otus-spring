--liquibase formatted sql

--changeset s.nykyforov:02-01
CREATE TABLE usr.book_comment
(
  id          SERIAL PRIMARY KEY,
  create_date TIMESTAMP WITH TIME ZONE NOT NULL,
  text        VARCHAR(255)             NOT NULL,
  book_id     INT                      NOT NULL CONSTRAINT fk_book_comment_book REFERENCES usr.book (id) ON DELETE CASCADE ON UPDATE CASCADE
);
