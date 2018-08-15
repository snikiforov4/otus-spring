package ua.nykyforov.service.library.application;

import ua.nykyforov.service.library.domain.Book;

import java.util.Collection;

public interface BookService {

    void save(Book author);

    Book getById(int id);

    void deleteById(int id);

    Collection<Book> findByTitle(String title);
}
