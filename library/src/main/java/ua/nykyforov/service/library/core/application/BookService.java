package ua.nykyforov.service.library.core.application;

import ua.nykyforov.service.library.core.domain.Book;

public interface BookService {

    void save(Book author);

    Book getById(int id);

}
