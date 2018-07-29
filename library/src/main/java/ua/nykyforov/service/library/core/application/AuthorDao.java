package ua.nykyforov.service.library.core.application;

import ua.nykyforov.service.library.core.domain.Author;

public interface AuthorDao {

    int insert(Author author);

    Author getById(int id);

}
