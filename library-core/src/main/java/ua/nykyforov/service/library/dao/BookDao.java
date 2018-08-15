package ua.nykyforov.service.library.dao;

import ua.nykyforov.service.library.domain.Book;

import java.util.Collection;

public interface BookDao {

    void insert(Book book);

    Book getById(int id);

    void deleteById(int id);

    Collection<Book> findByTitle(String query);
}
