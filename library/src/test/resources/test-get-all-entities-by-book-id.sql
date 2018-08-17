SET SCHEMA usr;

INSERT INTO book(id, title) VALUES (44, 'Effective Java (3rd Edition)');

INSERT INTO book_comment(id, create_date, text, book_id) VALUES (4, '2018-08-11 18:00:00+00', 'Awesome', 44);
INSERT INTO book_comment(id, create_date, text, book_id) VALUES (5, '2018-08-11 18:00:01+00', 'Must have', 44);
