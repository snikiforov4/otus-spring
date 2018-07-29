package ua.nykyforov.service.library.core.dao;

import ua.nykyforov.service.library.core.domain.Author;

public interface AuthorDao {

    int insert(Author author);

    Author getById(int id);

}
