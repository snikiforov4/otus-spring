package ua.nykyforov.service.library.core.application;

import ua.nykyforov.service.library.core.domain.Author;

import java.util.Optional;

public interface AuthorService {

    void save(Author author);

    Optional<Author> getById(int id);

}
