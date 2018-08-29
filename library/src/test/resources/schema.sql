CREATE SCHEMA IF NOT EXISTS usr;
SET SCHEMA usr;

CREATE SEQUENCE genre_id_seq;
CREATE TABLE usr.genre
(
   id integer default genre_id_seq.nextval PRIMARY KEY,
   name varchar(64) NOT NULL UNIQUE
);

CREATE SEQUENCE author_id_seq;
CREATE TABLE usr.author
(
   id integer default author_id_seq.nextval PRIMARY KEY,
   first_name varchar(64) NOT NULL,
   last_name varchar(64) NOT NULL,
);

CREATE SEQUENCE book_id_seq;
CREATE TABLE usr.book
(
   id integer default book_id_seq.nextval PRIMARY KEY,
   title varchar(256) NOT NULL,
   genre_id integer NULL,
   FOREIGN KEY(genre_id) REFERENCES genre(id) ON UPDATE CASCADE
);

CREATE TABLE usr.author_book
(
   author_id integer NOT NULL,
   book_id integer NOT NULL,
   PRIMARY KEY(author_id, book_id),
   FOREIGN KEY(author_id) REFERENCES author(id) ON UPDATE CASCADE,
   FOREIGN KEY(book_id) REFERENCES book(id) ON UPDATE CASCADE
);

CREATE SEQUENCE book_comment_id_seq;
CREATE TABLE book_comment
(
   id integer default book_comment_id_seq.nextval PRIMARY KEY,
   create_date TIMESTAMP WITH TIME ZONE NOT NULL,
   text varchar(255) NOT NULL,
   book_id integer NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(book_id) REFERENCES book(id) ON DELETE CASCADE
);
