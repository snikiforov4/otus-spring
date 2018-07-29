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