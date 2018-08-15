package ua.nykyforov.service.library.application;

import ua.nykyforov.service.library.domain.Author;

public interface AuthorService {

    void save(Author author);

    Author getById(int id);

}
