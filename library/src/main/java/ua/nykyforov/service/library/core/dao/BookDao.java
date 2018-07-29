package ua.nykyforov.service.library.core.dao;

import ua.nykyforov.service.library.core.domain.Book;

public interface BookDao {

    int insert(Book book);

    Book getById(int id);

}
