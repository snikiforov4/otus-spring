package ua.nykyforov.service.library.core.dao;

import ua.nykyforov.service.library.core.domain.Author;

public interface AuthorService {

    void save(Author author);

    Author getById(int id);

}
