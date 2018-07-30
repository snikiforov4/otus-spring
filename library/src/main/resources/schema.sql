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