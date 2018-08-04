package ua.nykyforov.service.library.core.application;

import ua.nykyforov.service.library.core.domain.Author;

public interface AuthorService {

    void save(Author author);

    Author getById(int id);

}
