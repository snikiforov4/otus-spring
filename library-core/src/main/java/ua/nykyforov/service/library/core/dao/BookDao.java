package ua.nykyforov.service.library.core.dao;

import ua.nykyforov.service.library.core.domain.Book;

import java.util.Collection;

public interface BookDao {

    void insert(Book book);

    Book getById(int id);

    void deleteById(int id);

    Collection<Book> findByTitleLike(String title);
}
