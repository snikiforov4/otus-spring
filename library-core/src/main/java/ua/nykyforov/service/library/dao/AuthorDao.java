package ua.nykyforov.service.library.dao;

import ua.nykyforov.service.library.domain.Author;

public interface AuthorDao {

    void insert(Author author);

    Author getById(int id);

}
