CREATE SCHEMA IF NOT EXISTS usr;
SET SCHEMA usr;

CREATE TABLE usr.genre
(
   id integer auto_increment PRIMARY KEY,
   name varchar(64) NOT NULL UNIQUE
);

CREATE TABLE usr.author
(
   id integer auto_increment PRIMARY KEY,
   first_name varchar(64) NOT NULL,
   last_name varchar(64) NOT NULL,
);

CREATE TABLE usr.book
(
   id integer auto_increment PRIMARY KEY,
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

CREATE TABLE book_comment
(
   id integer auto_increment PRIMARY KEY,
   create_date TIMESTAMP WITH TIME ZONE NOT NULL,
   text varchar(255) NOT NULL,
   book_id integer NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(book_id) REFERENCES book(id) ON DELETE CASCADE
);
