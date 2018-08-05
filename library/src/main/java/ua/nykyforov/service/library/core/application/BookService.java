package ua.nykyforov.service.library.core.application;

import ua.nykyforov.service.library.core.domain.Book;

import java.util.Collection;

public interface BookService {

    void save(Book author);

    Book getById(int id);

    void deleteById(int id);

    Collection<Book> findByTitleLike(String title);
}
