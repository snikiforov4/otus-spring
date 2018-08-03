package ua.nykyforov.service.library.core.dao;

import ua.nykyforov.service.library.core.domain.Book;

import java.util.Collection;

public interface BookDao {

    int insert(Book book);

    Book getById(int id);

    int deleteById(int id);

    Collection<Book> findByTitle(String query);
}
