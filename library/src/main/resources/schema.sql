CREATE TABLE genre
(
   id integer auto_increment NOT NULL,
   name varchar(64) NOT NULL,
   UNIQUE(name),
   PRIMARY KEY(id)
);

CREATE TABLE author
(
   id integer auto_increment NOT NULL,
   first_name varchar(64) NOT NULL,
   last_name varchar(64) NOT NULL,
   PRIMARY KEY(id)
);

CREATE TABLE book
(
   id integer auto_increment NOT NULL,
   title varchar(256) NOT NULL,
   genre_id integer NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(genre_id) REFERENCES genre(id) ON UPDATE CASCADE
);

CREATE TABLE author_book
(
   author_id integer NOT NULL,
   book_id integer NOT NULL,
   PRIMARY KEY(author_id, book_id),
   FOREIGN KEY(author_id) REFERENCES author(id) ON UPDATE CASCADE,
   FOREIGN KEY(book_id) REFERENCES book(id) ON UPDATE CASCADE
);

CREATE TABLE book_comment
(
   id integer auto_increment NOT NULL,
   create_date TIMESTAMP NOT NULL,
   text VARCHAR(255) NOT NULL,
   book_id integer NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(book_id) REFERENCES book(id) ON DELETE CASCADE
);
