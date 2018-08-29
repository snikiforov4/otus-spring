SET SCHEMA usr;

INSERT INTO author(id, first_name, last_name) VALUES (42, 'Joshua', 'Bloch');
INSERT INTO author(id, first_name, last_name) VALUES (43, 'Neal', 'Gafter');

INSERT INTO book(id, title) VALUES (23, 'Java Puzzlers');

INSERT INTO author_book(author_id, book_id) VALUES (42, 23);
INSERT INTO author_book(author_id, book_id) VALUES (43, 23);

