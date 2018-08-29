--liquibase formatted sql

--changeset s.nykyforov:01-01
CREATE TABLE usr.genre
(
  id   SERIAL PRIMARY KEY,
  name VARCHAR(64) NOT NULL CONSTRAINT genre_name_unique UNIQUE
);

--changeset s.nykyforov:01-02
CREATE TABLE usr.author
(
  id         SERIAL PRIMARY KEY,
  first_name VARCHAR(64) NOT NULL,
  last_name  VARCHAR(64) NOT NULL
);

--changeset s.nykyforov:01-03
CREATE TABLE usr.book
(
  id       SERIAL PRIMARY KEY,
  title    VARCHAR(256) NOT NULL,
  genre_id INT          NULL CONSTRAINT fk_book_genre REFERENCES usr.genre (id) ON DELETE RESTRICT ON UPDATE CASCADE
);

--changeset s.nykyforov:01-04
CREATE TABLE usr.author_book
(
  author_id INT NOT NULL CONSTRAINT fk_author_book REFERENCES usr.author (id) ON DELETE RESTRICT ON UPDATE CASCADE,
  book_id   INT NOT NULL CONSTRAINT fk_book_author REFERENCES usr.book (id) ON DELETE RESTRICT ON UPDATE CASCADE,
  PRIMARY KEY (author_id, book_id)
);
